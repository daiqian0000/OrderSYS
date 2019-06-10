package com.asion.config;

import com.asion.business.handler.security.shiro.cache.RedisCacheManager;
import com.asion.business.handler.security.shiro.filter.ShiroTokenFilter;
import com.asion.business.handler.security.shiro.realm.AuthRealm;
import com.asion.business.handler.security.shiro.session.CookieSessionIdGenerator;
import com.asion.business.handler.security.shiro.session.RedisSessionDAO;
import com.asion.business.handler.security.shiro.session.RestSessionManager;
import com.asion.business.web.filter.RestFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 * 
 * @author chenboyang
 *
 */
@Configuration
public class ShiroConfig {

	/**
	 * Shiro过滤器
	 * 
	 * @return 过滤器
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager());
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("restful", new RestFilter());
		filters.put("token", new ShiroTokenFilter());
		shiroFilter.setFilters(filters);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/isAuthen", "restful,anon");
		filterChainDefinitionMap.put("/login", "restful,anon");
		filterChainDefinitionMap.put("/**", "restful,token");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}

	/**
	 * 权限认证管理器
	 * 
	 * @return 认证管理器
	 */
	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setCacheManager(cacheManager());
		securityManager.setSessionManager(sessionManager());
		securityManager.setRealm(realm());
		return securityManager;
	}

	/**
	 * 会话缓存管理器
	 * 
	 * @return 缓存管理器
	 */
	@Bean
	public CacheManager cacheManager() {
		return new RedisCacheManager();
	}

	/**
	 * 会话管理器
	 * 
	 * @return 会话管理器
	 */
	@Bean
	public SessionManager sessionManager() {
		RestSessionManager sessionManager = new RestSessionManager();
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setCacheManager(cacheManager());
		return sessionManager;
	}

	/**
	 * 会话数据访问接口
	 * 
	 * @return 会话数据访问接口
	 */
	@Bean
	public SessionDAO sessionDAO() {
		RedisSessionDAO sessionDAO = new RedisSessionDAO();
		sessionDAO.setCacheManager(cacheManager());
		sessionDAO.setSessionIdGenerator(new CookieSessionIdGenerator());
		return sessionDAO;
	}

	/**
	 * 权限校验器
	 * 
	 * @return 校验器
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public Realm realm() {
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
		authRealm.setAuthorizationCachingEnabled(false);
		return authRealm;
	}

	/**
	 * 认证信息数据源
	 * 
	 * @return 认证信息数据源
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 生命周期处理器
	 * 
	 * @return 生命周期处理器
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 方法调用工厂
	 * 
	 * @return 调用工厂
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean() {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		methodInvokingFactoryBean.setArguments(securityManager());
		return methodInvokingFactoryBean;
	}

	/**
	 * 代理构建器
	 * 
	 * @return 构建器
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

}
