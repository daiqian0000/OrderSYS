package com.asion.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.asion.common.mybatis.plus.BusinessSqlInjector;
import com.asion.config.properties.DruidProperties;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisSqlSessionTemplate;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.toolkit.GlobalConfigUtils;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源配置类
 * 
 * @author chenboyang
 *
 */
@Configuration
@MapperScan(basePackages = "com.asion.business.**.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

	/**
	 * 数据源属性配置
	 * 
	 * @return 属性配置
	 */
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DruidProperties druidProperties() {
		return new DruidProperties();
	}

	/**
	 * 数据源
	 * 
	 * @return 数据源
	 * @throws SQLException
	 *             SQL异常
	 */
	@Bean(initMethod = "init", destroyMethod = "close")
	@Primary
	public DataSource dataSource() throws SQLException {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(druidProperties().getDriverClassName());
		dataSource.setUrl(druidProperties().getUrl());
		dataSource.setUsername(druidProperties().getUsername());
		dataSource.setPassword(druidProperties().getPassword());
		dataSource.setInitialSize(druidProperties().getInitialSize());
		dataSource.setMinIdle(druidProperties().getMinIdle());
		dataSource.setMaxActive(druidProperties().getMaxActive());
		dataSource.setMaxWait(druidProperties().getMaxWait());
		dataSource.setTimeBetweenEvictionRunsMillis(druidProperties().getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(druidProperties().getMinEvictableIdleTimeMillis());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties().getMaxPoolPreparedStatementPerConnectionSize());
		dataSource.setPoolPreparedStatements(druidProperties().isPoolPreparedStatements());
		dataSource.setValidationQuery(druidProperties().getValidationQuery());
		dataSource.setFilters(druidProperties().getFilters());
		return dataSource;
	}

	/**
	 * JDBC操作模板
	 * 
	 * @return 操作模板
	 * @throws SQLException
	 *             SQL异常
	 */
	@Bean
	@Primary
	public JdbcTemplate jdbcTemplate() throws SQLException {
		return new JdbcTemplate(dataSource());
	}

	/**
	 * 数据源事务管理器
	 * 
	 * @return 事务管理器
	 * @throws SQLException
	 *             SQL异常
	 */
	@Bean
	@Primary
	public DataSourceTransactionManager dataSourceTransactionManager() throws SQLException {
		return new DataSourceTransactionManager(dataSource());
	}

	/**
	 * MyBatis基础配置
	 * 
	 * @return 基础配置
	 */
	@Bean
	@Primary
	public MybatisConfiguration mybatisConfiguration() {
		MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
		mybatisConfiguration.setLazyLoadingEnabled(true);
		mybatisConfiguration.setAggressiveLazyLoading(true);
		mybatisConfiguration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
		mybatisConfiguration.setMapUnderscoreToCamelCase(true);
		mybatisConfiguration.addInterceptor(new PaginationInterceptor());
		return mybatisConfiguration;
	}

	/**
	 * MyBatis全局配置
	 * 
	 * @return 全局配置
	 */
	@Bean
	@Primary
	public GlobalConfiguration mybatisGlobalConfiguration() {
		GlobalConfiguration globalConfig = GlobalConfigUtils.defaults();
		globalConfig.setDbColumnUnderline(true);
		globalConfig.setSqlInjector(new BusinessSqlInjector());
		globalConfig.setCapitalMode(true);
		return globalConfig;
	}

	/**
	 * SQL会话工厂
	 * 
	 * @return 会话工厂
	 * @throws Exception
	 *             异常
	 */
	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		mybatisSqlSessionFactoryBean.setDataSource(dataSource());
		mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration());
		mybatisSqlSessionFactoryBean.setGlobalConfig(mybatisGlobalConfiguration());
		mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/asion/business/**Mapper.xml"));
		return mybatisSqlSessionFactoryBean.getObject();
	}

	/**
	 * MyBatis会话操作模板
	 * 
	 * @return 操作模板
	 * @throws Exception
	 *             异常
	 */
	@Bean
	@Primary
	public MybatisSqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new MybatisSqlSessionTemplate(sqlSessionFactory());
	}

}
