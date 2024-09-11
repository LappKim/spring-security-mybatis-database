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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(
        basePackages = "com.slowstarter.first",
        sqlSessionFactoryRef = "firstSqlSessionFactory"
)
public class FirstDatabaseConfig {
    @Primary
    @Bean
    @Qualifier( value = "firstDataSource" )
    @ConfigurationProperties(prefix = "spring.datasource.first")
    DataSource firstDataSource() {
        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
//                .username("test")
//                .password("test")
                .build();
    }
    @Primary
    @Bean
    @Qualifier( value = "firstSqlSessionFactory" )
    SqlSessionFactory firstSqlSessionFactory(@Qualifier("firstDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }
    @Primary
    @Bean
    @Qualifier( value = "firstTransactionManager" )
    DataSourceTransactionManager firstTransactionManager(@Qualifier("firstDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
