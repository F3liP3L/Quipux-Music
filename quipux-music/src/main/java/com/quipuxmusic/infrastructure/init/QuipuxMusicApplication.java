package com.quipuxmusic.infrastructure.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.quipuxmusic")
public class QuipuxMusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuipuxMusicApplication.class, args);
    }
}