package dev.uberlan.siscacs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SisCaCsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisCaCsApplication.class, args);
	}

	@Bean
	ApplicationRunner runner() {
		return (args) -> {
			System.out.println("running...");
		};
	}
}
