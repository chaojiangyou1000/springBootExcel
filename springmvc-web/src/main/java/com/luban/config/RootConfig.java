package com.luban.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 该类可以配置上下文中需要的 bean,如数据库,缓存等等,用于替代spring-dao.xml文件
 *
 * @author 晁江
 * @date:2019-05-17
 * @TODO:
 * @RootConfig
 * @com.luban.config
 */
@Configuration
@ComponentScan(basePackages = { "com.luban.config", "com.luban.mapper", "com.luban.service" })
// 在应用中，有时没有把某个类注入到IOC容器中，但在运用的时候需要获取该类对应的bean，此时就需要用到@Import注解
@Import(DruidDataSourceConfig.class)
public class RootConfig {

	/**
	 * 创建自动代理
	 * 
	 * @return BeanNameAutoProxyCreator
	 */
	@Bean
	public BeanNameAutoProxyCreator proxycreate() {
		BeanNameAutoProxyCreator proxycreate = new BeanNameAutoProxyCreator();
		proxycreate.setProxyTargetClass(true);
		proxycreate.setBeanNames("*ServiceImpl");
		proxycreate.setInterceptorNames("transactionInterceptor");
		return proxycreate;
	}
}