package com.example.api.client;

import com.example.api.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleWebClient {
    private final WebClient webClient;

    public ArticleDto create(ArticleDto dto) {
        //WebClient는 HTTP 요청을 Build한다고 생각해보자.
        ArticleDto response = webClient
                //POST 요청이다.
                .post()
                // uri 경로 설정
                .uri("/articles")
                //Body 설정
                .bodyValue(dto)
                // 여기부터 응답을 어떻게 처리할지
                .retrieve()
                //응답을 Mono<ArticleDto>롤 받는다. (반응형 아니면 mono)
                .bodyToMono(ArticleDto.class)
                // 응답을 대기해 결과를 돌려받는다. (동기식 처리)
                .block();
        log.info("response: {}", response);

        ResponseEntity<ArticleDto> responseEntity =
                webClient
                        //POST 요청이다.
                        .post()
                        // uri 경로 설정
                        .uri("/articles")
                        //Body 설정
                        .bodyValue(dto)
                        // 여기부터 응답을 어떻게 처리할지
                        .retrieve()
                        // ResponseEntity가 담긴 Mono를 받는다.
                        .toEntity(ArticleDto.class)
                        .block();
        log.info("responseEntity: {}", responseEntity);
        response = responseEntity.getBody();

        return response;
    }

    public ArticleDto readOne(Long id) {
        ArticleDto response = webClient
                .get()
                .uri("/articles/{id}", id)
                .retrieve()
                .bodyToMono(ArticleDto.class)
                .block();
        log.info("Read one ~ response: {}", response);

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
        response = webClient
                .get()
                .uri("/articles/{id}", uriVariables)
                .retrieve()
                .bodyToMono(ArticleDto.class)
                .block();
        log.info("Read One ~ map response: {}", response);
        return response;
    }

    public List<ArticleDto> readAll() {
        //ParameterizedTypeReference
        List<ArticleDto> response =  webClient.get()
                .uri("/articles")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ArticleDto>>() {
                })
                .block();
        log.info("ReadAll ~ response type: {}", response.getClass());
        return response;
    }

    public ArticleDto update(Long id, ArticleDto dto) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
        ArticleDto response = webClient.put()
                .uri("/articles/{id}", uriVariables)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ArticleDto.class)
                .block();
        log.info("response : {}", response);

        ResponseEntity<ArticleDto> responseEntity = webClient.put()
                .uri("/articles/{id}", uriVariables)
                .bodyValue(dto)
                .retrieve()
                .toEntity(ArticleDto.class)
                .block();
        log.info("ResponseEntity로 받기 response : {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public void delete(Long id) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
        ResponseEntity<?> responseEntity = webClient.delete()
                .uri("/articles/{id}", uriVariables)
                .retrieve()
                .toBodilessEntity()
                .block();
        log.info("delete status code: {}", responseEntity.getStatusCode());
    }

}
