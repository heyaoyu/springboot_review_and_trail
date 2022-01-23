package org.heyaoyu.tutorials.configurations

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultCloseableDSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class MysqlDataConfiguration {

    @ConfigurationProperties(prefix = "data.mysql.test")
    @Bean("mysqlDataSourceProperties")
    fun getDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean("mysqlDataSource")
    fun getMysqlDataSource(@Qualifier("mysqlDataSourceProperties") properties: DataSourceProperties): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(properties.driverClassName)
        dataSource.setUrl(properties.url)
        dataSource.setUsername(properties.username)
        dataSource.setPassword(properties.password)
        return dataSource
    }

    @Bean("mysqlJDBCTemplate")
    fun getMysqlDataTemplate(@Qualifier("mysqlDataSource") dataSrouce: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSrouce)
    }

    @Bean("dslContext")
    fun getDSLContext(@Qualifier("mysqlDataSource") dataSrouce: DataSource): DSLContext {
        return DefaultCloseableDSLContext(DataSourceConnectionProvider(dataSrouce), SQLDialect.MYSQL)
    }

}