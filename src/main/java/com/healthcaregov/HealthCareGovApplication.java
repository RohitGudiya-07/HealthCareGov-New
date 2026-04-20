package com.healthcaregov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HealthCareGovApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthCareGovApplication.class, args);
    }
}
