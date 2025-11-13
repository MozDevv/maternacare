package com.example.maternacare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MaternaCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaternaCareApplication.class, args);
    }

}
