package io.moun.api.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v2.0") //버전
                .title("Moun API") //이름
                .description("🎺 Music Auction Platform API 🎺"); //설명
        return new OpenAPI()
                .info(info)
                .components(
                        new Components()
                                .addSecuritySchemes("Authorization", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                )
                )
                .security(List.of(new SecurityRequirement().addList("Authorization")));

    }

}