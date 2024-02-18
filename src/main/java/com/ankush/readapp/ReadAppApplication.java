package com.ankush.readapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
public class ReadAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadAppApplication.class, args);
	}

}
