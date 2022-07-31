package com.io.linkapp.config;

import springfox.documentation.service.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket api(){
        
        //docket : 구성을 위한 여러가지 기본값과 편리한 방법을 제공함
        //.select()로 ApiSelectBuilder를 반환 받아 사용할 수 있게 해줌
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //  docket.useDefaultResponseMessages(false); - 확인해보기
        
        docket.ignoredParameterTypes(Pageable.class)
            .select().apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
        
        int tagOrd = 0;
        docket.tags(
            new Tag("Article", "아티클 API", ++tagOrd),
            new Tag("Tag", "태그 API", ++tagOrd),
            new Tag("Memo", "메모 API", ++tagOrd)
        );
        return docket;
    }
    
    
    //해당 swagger 문서 전체에 대한 info
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("DND 7TH I/O LINNKLE API")
            .version("1.0.0")
            .description("LINNKE API")
            .build();
    }
    
    // 완료가 되었으면 오른쪽 URL 로 접속 => http://localhost:8080/swagger-ui.html

}
