package com.shri.ShopNest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopNestApplication.class, args);
	}

}
