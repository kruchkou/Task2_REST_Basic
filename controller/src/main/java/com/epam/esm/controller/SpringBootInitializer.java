package com.epam.esm.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.epam.esm")
public class SpringBootInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootInitializer.class, args);
    }
}
