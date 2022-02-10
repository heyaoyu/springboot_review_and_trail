package org.heyaoyu.tutorials.configurations

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import com.mongodb.MongoClientURI

@Configuration
class MongoDataConfiguration {

    @ConfigurationProperties(prefix = "data.mongo.test")
    @Bean("mongoDataProps")
    fun getMongoDataProps(): MongoProperties = MongoProperties()

    @Bean("mongoDataFactory")
    fun getMongoFactory(@Qualifier("mongoDataProps") mongoProperties: MongoProperties): MongoDbFactory {
        return SimpleMongoDbFactory(MongoClientURI(mongoProperties.uri))
    }

    @Bean("mongoDataTemplate")
    fun getMongoDataTemplate(@Qualifier("mongoDataFactory") mongoDataFactory: MongoDbFactory): MongoTemplate {
        return MongoTemplate(mongoDataFactory)
    }
}