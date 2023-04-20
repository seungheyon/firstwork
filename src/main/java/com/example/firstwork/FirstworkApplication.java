package com.example.firstwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FirstworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstworkApplication.class, args);
    }

}
