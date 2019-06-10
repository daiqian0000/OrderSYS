package com.asion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用程序启动类
 * 
 * @author chenboyang
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan("com.asion")
@ServletComponentScan("com.asion")
public class Application {

	/**
	 * 程序主函数
	 * 
	 * @param args
	 *            参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
