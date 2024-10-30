package com.magasinpeche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MagasinPecheApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(MagasinPecheApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Permet de servir les fichiers du dossier uploads
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:uploads/");
	}
}
