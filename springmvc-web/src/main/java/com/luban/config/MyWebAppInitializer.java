package com.luban.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * WEB的初始化类
 * 
 * @author 晁江
 * @date:2019-05-17
 * @TODO:
 * @WebAppInitializer
 * @com.luban.config
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private final static Logger logger = LoggerFactory.getLogger(MyWebAppInitializer.class);

	/**
	 * 重写此方法,以替代 spring-context.xml在这个方法中返回的类类型数组可配置上下文中需要的 bean,如数据库,缓存等等
	 * 
	 * @return
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		logger.info("------root配置类初始化------");
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * 重写此方法,以替代 spring-mvc.xml,在这个方法中返回的类型数组可配置spring中视图解析器,http message
	 * converter等框架需要的 bean
	 * 
	 * @return
	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		logger.info("------web配置类初始化------");
		return new Class<?>[] { WebConfig.class };
	}

	/**
	 * 重写此方法,就相当于 <servlet-mapping>节点等同的功能
	 * 
	 * @return
	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings()
	 */
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		logger.info("------映射根路径初始化------");
		return new String[] { "/" };
	}
}