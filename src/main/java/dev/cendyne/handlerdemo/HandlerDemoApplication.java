package dev.cendyne.handlerdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HandlerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandlerDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(DynamicFrontendService dynamicFrontend) {
		return (args) -> {
			dynamicFrontend.addHtml("/hello", "<h1>Hello World</h1>");
			dynamicFrontend.addHtml("/aloha", "<h1>Aloha kakahiaka</h1>");
		};
	}
}
