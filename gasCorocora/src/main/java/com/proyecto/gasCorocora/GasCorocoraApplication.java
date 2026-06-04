package com.proyecto.gasCorocora;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class GasCorocoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(GasCorocoraApplication.class, args);
	}

}
