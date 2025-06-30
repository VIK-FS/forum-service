package ait.cohort5860.configuration;

import ait.cohort5860.accounting.dao.UserAccountRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ait.cohort5860.accounting.dao")
public class MongoConfig {
}
