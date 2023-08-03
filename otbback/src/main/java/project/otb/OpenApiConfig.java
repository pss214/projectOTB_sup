package project.otb;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger springdoc-ui 구성 파일
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("OTB 프로젝트 back API")
                .version("v0.0.1")
                .description("OTB 프로젝트의 API 리스트 입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}