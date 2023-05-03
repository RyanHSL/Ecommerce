package com.mrui22.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan(basePackages = {"com.mrui22.ecommerce.controller", "com.mrui22.ecommerce.model", "com.mrui22.ecommerce.services"})
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}


}
