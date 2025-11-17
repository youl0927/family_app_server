package com.family.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    public final static String NAME = "AuthorizationHeader";
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .version("v1.0")
                .title("Family_App API")
                .description("패밀리 앱 api");

        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");


        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(NAME);

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(NAME, apiKey))
                .addSecurityItem(securityRequirement)
                .info(info);
    }
}
