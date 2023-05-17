package com.darkzera.fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@SpringBootApplication
@ConfigurationProperties
public class FetcherApplication {

	/* TODO
			- Fix DB q no final nao serviu p. mta coisa =)
			- Tirei o OAUTH2 da google. Depois da att deles nao consegui mais usar o Postman
			- Refatoracao da transformacao da resposta Spotify para o modelo de negocio.
				: Agora num componente especialista ReorganizeArtistData.java

	 */


	public static void main(String[] args) {
		SpringApplication.run(FetcherApplication.class, args);

	}
	@Configuration
	public class AppConfig {
		@Bean
		public Clock clock() {
			return Clock.systemDefaultZone();
		}
	}
}
