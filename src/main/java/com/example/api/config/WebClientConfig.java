package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient defaultWebClient() {
        // 그냥 새로 생성해서 사용할수도 있음
        // WebClient webClient = WebClient.create();

        // RestTemplate에 비해 선언형 (함수형) 구조를 가진다.
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                //처음부터 Header를 기록해두기 때문에 바꿀수가 없다.
                .defaultHeader("test", "foo")
                //request의 header의 default값을 설정해준거기 때문에 바꿀 수 있다.
                .defaultRequest(request -> request.header("test", "value"))
                .defaultStatusHandler(
                        HttpStatusCode::isError,
                        response -> {
                            throw new ResponseStatusException(response.statusCode());
                        }
                )
                .build();
    }
}
