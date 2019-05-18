package com.luban.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源属性配置
 *
 * @author 晁江
 * @date:2019-05-18
 * @TODO:
 * @DruidDataSourceConfig
 * @com.luban.config
 */
@Configuration
@PropertySource("classpath:/jdbc.properties")
@MapperScan(basePackages = "com.luban.mapper")
public class DruidDataSourceConfig {

	private final static Logger logger = LoggerFactory.getLogger(DruidDataSourceConfig.class);

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	/**
	 * 数据源配置
	 * 
	 * @return DataSource
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("Initialize the data source...");
		DruidDataSource datasource = new DruidDataSource();

		datasource.setUrl(this.dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);

		// configuration
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		return datasource;
	}

	/**
	 * spring整合mybatis配置
	 * 
	 * @return
	 * @throws IOException SqlSessionFactoryBean
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		// mapper.xml文件路径
		sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:com/luban/mapper/*.xml"));
		// 别名，让*Mpper.xml实体类映射可以不加上具体包名
		sqlSessionFactoryBean.setTypeAliasesPackage("com.luban.entity");
		return sqlSessionFactoryBean;
	}

	/**
	 * 事务配置
	 * 
	 * @return DataSourceTransactionManager
	 */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}

	/**
	 * 事务传播行为配置
	 * 
	 * @return TransactionInterceptor
	 */
	@Bean(name = "transactionInterceptor")
	public TransactionInterceptor interceptor() {
		TransactionInterceptor interceptor = new TransactionInterceptor();
		interceptor.setTransactionManager(dataSourceTransactionManager());

		Properties transactionAttributes = new Properties();
		transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
		transactionAttributes.setProperty("del*", "PROPAGATION_REQUIRED");
		transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
		transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
		transactionAttributes.setProperty("find*", "PROPAGATION_REQUIRED,readOnly");
		transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");

		interceptor.setTransactionAttributes(transactionAttributes);
		return interceptor;
	}
}