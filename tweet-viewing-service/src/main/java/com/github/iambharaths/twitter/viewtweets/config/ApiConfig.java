package com.github.iambharaths.twitter.viewtweets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApiConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
													  .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
													  .paths(PathSelectors.any())
													  .build()
													  .apiInfo(apiEndPointsInfo())
													  .tags(new Tag("Tweets API", " "));
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("Tweet Viewing Service")
								   .description("Service to fetch Tweets sorted by the date of creation, Get/Post/Delete rules to follow")
								   .version("v1")
								   .license(" ")
								   .contact(new Contact(" ", " ", " "))
								   .licenseUrl(" ")
								   .termsOfServiceUrl(" ")
								   .build();
	}
}
