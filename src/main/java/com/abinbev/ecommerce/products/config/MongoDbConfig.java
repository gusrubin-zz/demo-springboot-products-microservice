package com.abinbev.ecommerce.products.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoDbConfig {

	@Value("${spring.data.mongodb.host}")
	private String mongoDbServerHost;

	@Value("${spring.data.mongodb.port}")
	private String mongoDbServerPort;

	@Value("${spring.data.mongodb.database}")
	private String mongoDbServerDatabase;

	@Bean
	public MongoClient mongoClient() {
		String connectionUri = "mongodb://" + mongoDbServerHost + ":" + mongoDbServerPort + "/" + mongoDbServerDatabase;
		return MongoClients.create(connectionUri);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), mongoDbServerDatabase);
	}

}
