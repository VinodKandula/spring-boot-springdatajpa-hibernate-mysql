/**
 * 
 */
package com.teqnihome;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author vkandula
 *
 */
@Component
public class PropertyConfig {

	@Value("${spring.datasource.driver-class-name}")
    public String dataSourceClassName;

    @Value("${spring.datasource.driver-class-name}")
    public String jdbcUrl;

    @Value("${spring.datasource.username}")	
    public String jdbcUser;

    @Value("${spring.datasource.password}")
    public String jdbcPassword;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    public String hibernateDialect;
    
    @Resource
    public Environment environment;
	
    @PostConstruct
    public void init() {
        System.out.println("================== " + jdbcUrl + "================== ");
    }
}
