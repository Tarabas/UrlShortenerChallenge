package net.rorarius.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackages = { "net.rorarius.repository", "net.rorarius.data" })
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Bean
    @Override
    public Mongo mongo() {
        return new Fongo("testdb").getMongo();
    }

    @Override
    protected String getDatabaseName() {
        return "testdb";
    }

    public @Bean MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), "testdb");
        return mongoTemplate;
    }
}