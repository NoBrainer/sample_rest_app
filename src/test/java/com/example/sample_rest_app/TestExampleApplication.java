package com.example.sample_rest_app;

import org.springframework.boot.SpringApplication;

public class TestExampleApplication {

    public static void main(String[] args) {
        SpringApplication.from(ExampleApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
