package com.kood.dev.springboot2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Springboot2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2DemoApplication.class, args);
    }

}
