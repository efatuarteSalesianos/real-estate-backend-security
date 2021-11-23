package com.salesianostriana.dam.realestatesecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealEstateSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateSecurityApplication.class, args);
	}

}
