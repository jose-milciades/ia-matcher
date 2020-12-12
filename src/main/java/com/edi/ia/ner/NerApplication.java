package com.edi.ia.ner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.edi.ia.ner"})
public class NerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NerApplication.class, args);
	}

}
