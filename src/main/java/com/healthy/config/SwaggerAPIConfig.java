package com.healthy.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerAPIConfig {

    @Value("${healthyy.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Healthy Dev Server");

        //INFORMACION DE CONTACTO
        Contact contact = new Contact();
        contact.setUrl("https://google.com");
        contact.setName("Healthy");
        contact.setEmail("healthy@gmail.com");

        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        //INFORMACION DE LA API

        Info info = new Info()
                .title("API Healthy app para habitos saludables")
                .version("1.0")
                .contact(contact)
                .description("API Restful de habitos saludables")
                .termsOfService("https://github.com/Code-Healthy/Healthyy")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .addServersItem(devServer);

    }
}
