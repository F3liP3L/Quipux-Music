package com.quipuxmusic.infrastructure.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.quipuxmusic.core.domain.entities")
@EnableJpaRepositories(basePackages = "com.quipuxmusic.infrastructure.adapter.secondary.repository")
public class JpaConfig {
}