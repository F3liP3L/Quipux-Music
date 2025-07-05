package com.quipuxmusic.infrastructure.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static com.quipuxmusic.utils.constant.QuipuxMusicCostant.ENTITY_PACKAGE;
import static com.quipuxmusic.utils.constant.QuipuxMusicCostant.REPOSITORY_PACKAGE;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ENTITY_PACKAGE )
@EnableJpaRepositories(basePackages = REPOSITORY_PACKAGE)
public class JpaConfig {
}