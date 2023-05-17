package com.spring.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class RestapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

}
