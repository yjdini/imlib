package com.ini;


import com.db.mongod.core.MongoClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public MongoTemplate mongoTemplate()
    {
        System.out.println("create bean mongoTemplate");
        return new MongoTemplate(MongoClientFactory.getMongoClient(), "test");
    }
}
