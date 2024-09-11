package com.slowstarter.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(
        basePackages = "com.slowstarter.second",
        sqlSessionFactoryRef = "secondSqlSessionFactory"
)
public class SecondDatabaseConfig {
    @Bean
    @Qualifier( value = "secondDataSource" )
    @ConfigurationProperties(prefix = "spring.datasource.second")
    DataSource secondDataSource() {
        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/test2?serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
//                .username("test")
//                .password("test")
                .build();
    }
    @Bean
    @Qualifier( value = "secondSqlSessionFactory" )
    SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }
    @Bean
    @Qualifier( value = "secondTransactionManager" )
    DataSourceTransactionManager secondTransactionManager(@Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
