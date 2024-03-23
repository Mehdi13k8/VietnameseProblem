package com.skazy.vietnameseSolutioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VietnameseSolutionerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VietnameseSolutionerApplication.class, args);
	}

}
