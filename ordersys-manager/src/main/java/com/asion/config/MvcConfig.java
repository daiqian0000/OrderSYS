package com.asion.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.asion.business.module.log.interceptor.LogInterceptor;
import com.asion.business.module.log.service.WebLogService;
import com.asion.business.web.filter.XssFilter;
import com.asion.business.web.resolver.RequestJsonResolver;
import com.asion.business.web.resolver.RequestModelResolver;
import com.asion.common.aop.ControllerReturnAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletException;
import java.util.List;

/**
 * MVC配置类
 * 
 * @author chenboyang
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * Web日志服务接口
	 */
	@Autowired
	private WebLogService webLogService;

	/**
	 * 控制器返回统一处理切面
	 * 
	 * @return 控制器返回统一处理切面
	 */
	@Bean
	public ControllerReturnAspect controllerReturnAspect() {
		return new ControllerReturnAspect();
	}

	/**
	 * XSS漏洞防御过滤器
	 * 
	 * @return 过滤器
	 */
	@Bean
	public FilterRegistrationBean xssFilter() {
		FilterRegistrationBean filterRegistrator = new FilterRegistrationBean();
		filterRegistrator.setFilter(new XssFilter());
		filterRegistrator.addUrlPatterns("/*");
		return filterRegistrator;
	}

	/**
	 * Druid服务器信息过滤器
	 * 
	 * @return 过滤器
	 */
	@Bean
	public FilterRegistrationBean druidFilter() {
		FilterRegistrationBean filterRegistrator = new FilterRegistrationBean();
		try {
			WebStatFilter webStatFilter = new WebStatFilter();
			MockFilterConfig mockFilterConfig = new MockFilterConfig();
			mockFilterConfig.addInitParameter(WebStatFilter.PARAM_NAME_EXCLUSIONS, "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
			webStatFilter.init(mockFilterConfig);
			filterRegistrator.setFilter(webStatFilter);
			filterRegistrator.addUrlPatterns("/*");
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return filterRegistrator;
	}

	/**
	 * Druid服务器信息Servlet访问接口
	 * 
	 * @return Servlet访问接口
	 */
	@Bean
	public ServletRegistrationBean druidServlet() {
		return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
	}

	/**
	 * 添加参数解析器
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new RequestModelResolver());
		argumentResolvers.add(new RequestJsonResolver());
		super.addArgumentResolvers(argumentResolvers);
	}

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor(webLogService))
			.addPathPatterns("/**")
			.excludePathPatterns(
				"/login",
				"/logout",
				"/druid/**");
		super.addInterceptors(registry);
	}

	/**
		 * 添加跨域设置
		 */
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry
					.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("*")
					.allowCredentials(false)
					.maxAge(60 * 60);
			super.addCorsMappings(registry);
	}

}
