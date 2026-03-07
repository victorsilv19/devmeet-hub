package com.devmeethub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DevMeetHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevMeetHubApplication.class, args);
	}

	// Cliente base para consumir APIs Rest Externas (ViaCEP)
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
