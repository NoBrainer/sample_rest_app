package com.example.sample_rest_app;

import org.springframework.boot.SpringApplication;

public class TestSampleRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(SampleRestAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
