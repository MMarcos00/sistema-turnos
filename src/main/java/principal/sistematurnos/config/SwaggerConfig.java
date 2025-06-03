package principal.sistematurnos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Turnos - API")
                        .version("1.0")
                        .description("Documentación generada automáticamente con Swagger para el sistema de turnos desarrollado por Marcos."));
    }
}
