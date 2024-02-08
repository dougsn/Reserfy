package br.reserfy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(name = "Bearer Authentication" , scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "bearer")
@OpenAPIDefinition(
		info = @Info(
				title = "API REST Reserfy",
				version = "1.0.0",
				description = "API REST Reserfy",
				contact = @Contact(
						name = "Reserfy"
				)
		)
)
@SpringBootApplication
public class ReserfyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReserfyApplication.class, args);
	}

}
