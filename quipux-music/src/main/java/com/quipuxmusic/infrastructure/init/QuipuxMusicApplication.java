package com.quipuxmusic.infrastructure.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static com.quipuxmusic.utils.constant.QuipuxMusicCostant.BASE_PACKAGE;

@SpringBootApplication
@ComponentScan(basePackages = BASE_PACKAGE)
public class QuipuxMusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuipuxMusicApplication.class, args);
    }
}