package org.heyaoyu.tutorials.configurations

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

@Configuration
class MongoDataConfigruation {

    @ConfigurationProperties(prefix = "data.mongo.test")
    @Bean("mongoDataProps")
    fun getMongoDataProps(): MongoProperties {
        val ret = MongoProperties()
        return ret
    }

    @Bean("mongoDataFactory")
    fun getMongoFactory(@Qualifier("mongoDataProps") mongoProperties: MongoProperties): MongoDatabaseFactory {
        println("########${mongoProperties.uri}")
        return SimpleMongoClientDatabaseFactory(mongoProperties.uri)
    }

    @Bean("mongoDataTemplate")
    fun getMongoDataTemplate(@Qualifier("mongoDataFactory") mongoDataFactory: MongoDatabaseFactory): MongoTemplate {
        return MongoTemplate(mongoDataFactory)
    }
}