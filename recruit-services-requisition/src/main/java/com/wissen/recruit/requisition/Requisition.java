package com.wissen.recruit.requisition;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@ComponentScan
public class Requisition {

	public static void main(String[] args) {
		SpringApplication.run(Requisition.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.paths(PathSelectors.any()) // for which EndPoints
				.apis(RequestHandlerSelectors.basePackage("com.wissen.recruit.requisition.controller"))  // Generate API of EndPoints which is available inside defined package
				.build() // create object
				.apiInfo(apiInfo());
	}


	private ApiInfo apiInfo() {
		return new ApiInfo("RecruIT API Documentation", "APIs Documentation", "Beta 1.0", "Terms of service - Free to use",
				new springfox.documentation.service.Contact("Wissen Infotech", "www.wisseninfotech.com", "recruIT@wissenInfotech.com"), "License-RecuritIT API",
				"API license URL", Collections.emptyList());
	}

}
