package com.rabo.statement.swaggerconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${company.name}")
	String companyName;

	@Value("${company.website}")
	String companyWebsite;

	@Value("${company.email}")
	String companyEmail;

	@Value("${company.license.url}")
	String companylicenseUrl;

	@Value("${api.version}")
	String apiVersion;

	@Value("${api.description}")
	String apiDescription;

	@Value("${api.title}")
	String apiTitle;

	@Value("${swagger.config.api.basepackage}")
	String basePackage;

	@Value("${swagger.config.api.path}")
	String swaggerPath;

	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.regex(swaggerPath)).build().apiInfo(apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title(apiTitle).description(apiDescription)
				.contact(new Contact(companyName, companyWebsite, companyEmail))
				.license("").licenseUrl(companylicenseUrl).version("1.0.0")
				.build();
	}
}