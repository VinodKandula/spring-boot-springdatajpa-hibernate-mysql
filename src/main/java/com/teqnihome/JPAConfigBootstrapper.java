/**
 * 
 */
package com.teqnihome;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * @author vkandula
 *
 */
public class JPAConfigBootstrapper {
	
	@Autowired
    public DataSource dataSource; // TODO doesn't work
	
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	
	@Value("${spring.datasource.driver-class-name}")
    private String dataSourceClassName;

    @Value("${spring.datasource.driver-class-name}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")	
    private String jdbcUser;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    private static JPAConfigBootstrapper _INSTANCE = null;
    
    private static LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = null;
    
    private static EntityManagerFactory entityManagerFactory = null;
    
    private static EntityManager entityManager = null;
    
    private static JpaTransactionManager jpaTransactionManager = null;
    
    private JPAConfigBootstrapper() {
    	
    }
    
    public static JPAConfigBootstrapper getInsatnce() {
    	synchronized(JPAConfigBootstrapper.class) {
    		if (_INSTANCE == null) {
    			localContainerEntityManagerFactoryBean = entityManagerFactory();
    			
    			entityManagerFactory = localContainerEntityManagerFactoryBean.getNativeEntityManagerFactory();
    			
    			//entityManager = entityManagerFactory.createEntityManager();
    			
    			jpaTransactionManager = transactionManager(entityManagerFactory);
    			
    			_INSTANCE = new JPAConfigBootstrapper();
    		}
    		
    		return _INSTANCE;
    	}
    }
    
    public EntityManager createEntityManager() {
    	//entityManager is not thread safe
    	return entityManagerFactory.createEntityManager();
    }
    
    public JpaTransactionManager getJpaTransactionManager() {
    	return transactionManager(entityManagerFactory);
    }
    
	private static DataSource createDataSource() {
		DataSourceBuilder factory = DataSourceBuilder.create(JPAConfigBootstrapper.class.getClassLoader())
				.driverClassName("com.mysql.jdbc.Driver")
				.url("jdbc:mysql://localhost:3306/test?useSSL=false")
				.username("root")
				.password("root");
		return factory.build();
	}
	
	@Deprecated
	public DataSource dataSource() {
		//return (DataSource)context.getBean("dataSource");
		return dataSource;
	}

	private static LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setPersistenceUnitName(JPAConfigBootstrapper.class.getSimpleName());
		localContainerEntityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
		localContainerEntityManagerFactoryBean.setDataSource(createDataSource());
		localContainerEntityManagerFactoryBean.setPackagesToScan(packagesToScan());

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		localContainerEntityManagerFactoryBean.setJpaProperties(additionalProperties());
		
		localContainerEntityManagerFactoryBean.afterPropertiesSet();
		return localContainerEntityManagerFactoryBean;
	}

	private static Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		return properties;
	}
	
	private static String[] packagesToScan() {
        return new String[]{
            "com.bytecode.gen"
            + ""
        };
    }
	
	private static JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
