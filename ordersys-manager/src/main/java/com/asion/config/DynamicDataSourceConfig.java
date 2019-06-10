package com.asion.config;

import com.asion.common.db.DynamicDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisSqlSessionTemplate;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源配置类
 * 
 * @author chenboyang
 *
 */
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@MapperScan(basePackages = "com.asion.dynamic.**.mapper", sqlSessionFactoryRef = "dynamicSqlSessionFactory")
public class DynamicDataSourceConfig {

	/**
	 * 数据源
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * MyBatis基础配置
	 */
	@Autowired
	private MybatisConfiguration mybatisConfiguration;

	/**
	 * MyBatis全局配置
	 */
	@Autowired
	private GlobalConfiguration mybatisGlobalConfiguration;

	/**
	 * 动态数据源
	 * 
	 * @return 数据源
	 * @throws SQLException
	 *             SQL异常
	 */
	@Bean
	public DynamicDataSource dynamicDataSource() throws SQLException {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setDefaultTargetDataSource(dataSource);
		dynamicDataSource.setTargetDataSources(new ConcurrentHashMap<Object, Object>());
		return dynamicDataSource;
	}

	/**
	 * 动态JDBC操作模板
	 * 
	 * @return 操作模板
	 * @throws SQLException
	 *             SQL异常
	 */
	@Bean
	public JdbcTemplate dynamicJdbcTemplate() throws SQLException {
		return new JdbcTemplate(dynamicDataSource());
	}

	/**
	 * 动态数据源事务管理器
	 * 
	 * @return 事务管理器
	 * @throws SQLException
	 *             SQL异常
	 */
	@Bean
	public DataSourceTransactionManager dynamicDataSourceTransactionManager() throws SQLException {
		return new DataSourceTransactionManager(dynamicDataSource());
	}

	/**
	 * 动态SQL会话工厂
	 * 
	 * @return 会话工厂
	 * @throws Exception
	 *             异常
	 */
	@Bean
	public SqlSessionFactory dynamicSqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		mybatisSqlSessionFactoryBean.setDataSource(dynamicDataSource());
		mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
		mybatisSqlSessionFactoryBean.setGlobalConfig(mybatisGlobalConfiguration);
		mybatisSqlSessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:com/asion/dynamic/**Mapper.xml"));
		return mybatisSqlSessionFactoryBean.getObject();
	}

	/**
	 * 动态MyBatis会话操作模板
	 * 
	 * @return 操作模板
	 * @throws Exception
	 *             异常
	 */
	@Bean
	public MybatisSqlSessionTemplate dynamicSqlSessionTemplate() throws Exception {
		return new MybatisSqlSessionTemplate(dynamicSqlSessionFactory());
	}

}
