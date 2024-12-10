package com.example.SGR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgrApplication.class, args);
	}

}
