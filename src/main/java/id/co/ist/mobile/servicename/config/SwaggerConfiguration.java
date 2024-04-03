package id.co.ist.mobile.servicename.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
@EnableOpenApi
public class SwaggerConfiguration {

    @Value("${enable.swagger:true}")
    private boolean enableSwagger;
    @Value("${spring.application.name:DBank-backend}")
    private String applicationName;
    @Value("${swagger.application.description:new DBank backend api specification}")
    private String applicationDescription;
    @Value("${application.version:1}")
    private String applicationVersion;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("id.co.ist.mobile"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enableSwagger);

    }

    @Bean
    public ApiInfo apiInfo() {
        String danamonUrl = "https://www.danamon.co.id";
        return new ApiInfo("REST API Documentation of ".concat(applicationName),
                "",
                "v".concat(applicationVersion),
                "danamon.co.id",
                new Contact("Danamon", danamonUrl, ""),
                "Danamon",
                danamonUrl,
                Collections.emptyList());
    }


}
