package com.epam.esm.repository.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.epam.esm.repository")
@EnableTransactionManagement
public class RepositoryConfig {

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean(name = "dataSource")
    @Profile("prod")
    public DataSource prodDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://185.135.80.244:3306/gifts");
        dataSource.setUsername("baker");
        dataSource.setPassword("BazaBaker1");
        dataSource.setInitialSize(10);
        return dataSource;
    }

    @Bean(name = "dataSource")
    @Profile("dev")
    public DataSource devDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://185.135.80.244:3306/gifts");
        dataSource.setUsername("baker");
        dataSource.setPassword("BazaBaker1");
        dataSource.setInitialSize(10);
        return dataSource;
    }


}
