package za.ac.cput.config;

// These imports are CRITICAL. If they are missing, you get "Cannot resolve symbol"
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI placementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nursing Placement API")
                        .description("API for managing Student/Staff placements and preferences")
                        .version("1.0.0"));
    }
}