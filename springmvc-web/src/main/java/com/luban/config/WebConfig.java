package com.luban.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 该类可以配置视图解析器,用于替代spring-mvc.xml文件
 *
 * @author 晁江
 * @date:2019-05-17
 * @TODO:
 * @WebConfig
 * @com.luban.config
 */
@Configuration
@EnableWebMvc // 开启springMVC
@ComponentScan("com.luban.controller") // 启动组件扫描只扫描controller层
@SuppressWarnings("deprecation")
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置JSP视图解析器
	 * 
	 * @return ViewResolver
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setExposeContextBeansAsAttributes(true);
		return internalResourceViewResolver;
	}

	/**
	 * 文件上传，bean必须写name属性且必须为multipartResolver，不然取不到文件对象
	 * 
	 * @return CommonsMultipartResolver
	 */
	@Bean(name = "multipartResolver")
	protected CommonsMultipartResolver MultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		// 可不设置
		// multipartResolver.setUploadTempDir(new FileSystemResource("/tmp"));
		// 2M
		multipartResolver.setMaxUploadSize(2097152);
		multipartResolver.setMaxInMemorySize(0);
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
	}

	/**
	 * 配置静态资源的处理
	 * 
	 * @param configurer
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureDefaultServletHandling(org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer)
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		super.configureDefaultServletHandling(configurer);
		// 对静态资源的请求转发到容器缺省的servlet，而不使用DispatcherServlet
		configurer.enable();
	}
}