package com.example.ratelimiterbucket4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Ratelimiterbucket4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ratelimiterbucket4jApplication.class, args);
    }

}
