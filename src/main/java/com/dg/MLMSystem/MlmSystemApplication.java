package com.dg.MLMSystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot3 with new version of Swagger UI",
				version="1.0.0",
				description = "Spring Boot3 with Swagger UI",
				termsOfService = "",
				contact = @Contact(
						name = "Divya Gujjuka",
						email = "gujjukadivya0901@gmail.com"
				),
				license = @License(
						name = "licence",
						url = ""
				)
		)
)
public class MlmSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlmSystemApplication.class, args);
	}

}
