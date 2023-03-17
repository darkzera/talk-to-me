package com.darkzera.fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
@ConfigurationProperties
public class FetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(FetcherApplication.class, args);

	}

}
