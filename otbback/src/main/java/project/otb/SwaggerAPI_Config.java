package project.otb;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI : swagger 문서 main 글

 * GroupedOpenAPI : url 포함 및 제외 함수

 */
@Configuration
public class SwaggerAPI_Config {
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
    @Bean
    public GroupedOpenApi apiV1(){
        return GroupedOpenApi.builder()
                .group("api-varsion-1")
                .pathsToMatch("/**")
                .pathsToExclude("/admin/**")
                .build();
    }
}