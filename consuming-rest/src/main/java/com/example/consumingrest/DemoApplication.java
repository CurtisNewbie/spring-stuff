package com.example.consumingrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	public static final String URL = "https://gturnquist-quoters.cfapps.io/api/random";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * Run on startup, this method has to be in @SpringBootApplication, RestTemplate
	 * is injected automatically
	 */
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			log.info("Application Started, Fetch Quote");
			Quote quote = restTemplate.getForObject(URL, Quote.class);
			log.info(quote.toString());
		};
	}

	/**
	 * Produces a RestTemplate to process received JSON data, We can use WebClient
	 * instead (which is new)
	 */
	@Bean
	public RestTemplate restTemplateProducer(RestTemplateBuilder builder) {
		return builder.build();
	}
}
