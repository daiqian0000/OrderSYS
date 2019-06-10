package com.asion.config;

import com.asion.common.io.storage.FileStorage;
import com.asion.common.io.storage.SimpleFileStorage;
import com.asion.config.properties.SysProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 基础配置类
 * 
 * @author chenboyang
 *
 */
@Configuration
public class BaseConfig {

	/**
	 * 系统业务属性配置
	 * 
	 * @return 属性配置
	 */
	@Bean
	@ConfigurationProperties("sys")
	public SysProperties sysProperties() {
		return new SysProperties();
	}

	/**
	 * REST接口访问操作模板
	 * 
	 * @return 操作模板
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * 文件存储服务接口
	 * 
	 * @return 文件存储服务接口
	 */
	@Bean
	public FileStorage fileStorage() {
		return new SimpleFileStorage();
	}

}
