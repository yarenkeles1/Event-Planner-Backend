package com.yaren.eventplanner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Etkinlik Planlama API")
                        .description("Etkinlik Planlama Uygulaması REST API Dokümantasyonu")
                        .version("1.0.0")
                );
    }
}