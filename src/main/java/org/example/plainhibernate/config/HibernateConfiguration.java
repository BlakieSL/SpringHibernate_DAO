package org.example.plainhibernate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {
    private final DataSource dataSource;

    @Autowired
    public HibernateConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    @ConfigurationProperties("spring.jpa.properties")
    public Properties hibernateProperties() {
        return new Properties();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("org.example.plainhibernate.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
}
