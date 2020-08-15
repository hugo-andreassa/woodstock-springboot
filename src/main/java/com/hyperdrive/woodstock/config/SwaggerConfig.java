package com.hyperdrive.woodstock.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** Classe de configuração do Swagger
 * 
 * @author Hugo Andreassa Amaral 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.hyperdrive.woodstock.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"API do projeto WoodStock",
				"Está API será utilizada pelo aplicativo WoodStock", 
				"Versão 1.0",
				"",
				new Contact(
						"Hugo Andreassa Amaral", 
						"", 
						"hugo.andreassa@gmail.com"),
				"", 
				"", 
				Collections.emptyList()				
		);
	}
}
