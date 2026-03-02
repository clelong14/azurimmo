package bts.sio.azurimmo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI azurimmoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Azurimmo API")
                        .description("API REST pour la gestion des bâtiments et appartements")
                        .version("v1.0.1")
                        .contact(new Contact()
                                .name("Équipe Azurimmo")
                                .email("contact@azurimmo.com")
                                .url("https://www.azurimmo.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}