package com.example.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    //RestTemplateBuilder를 활용해 전체 서비스에서 사용할
    //기본 설정을 갖춘 RestTemplate을 Bean으로 등록 가능.
    @Bean
    public RestTemplate defaultRestTemplate(
            RestTemplateBuilder templateBuilder
    ) {
        //그냥 새로 생성해서 사용할 수도 잇음
//        RestTemplate restTemplate = new RestTemplate();
        return templateBuilder.rootUri("http://localhost:8081").build();
    }
}
