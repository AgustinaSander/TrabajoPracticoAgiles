package com.tpagiles;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.tpagiles.models")
@EnableJpaRepositories(basePackages = "com.tpagiles.repositories")
public class ClassConfig {

}
