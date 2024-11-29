package com.stqap.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.stqap.web", "com.stqap.calculatorlogic"})
public class SweQaProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SweQaProjectApplication.class, args);
    }
}
