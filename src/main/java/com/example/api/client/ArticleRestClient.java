package com.example.api.client;

import com.example.api.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleRestClient {
    private final RestClient restClient;

    public ArticleDto create(ArticleDto dto) {
        ArticleDto response = restClient.post()
                .uri("/articles")
                .body(dto)
                .retrieve()
                .body(ArticleDto.class);
        log.info("response: {}", response);

        ResponseEntity<ArticleDto> responseEntity =
                restClient.post()
                        .uri("/articles")
                        .body(dto)
                        .retrieve()
                        .toEntity(ArticleDto.class);
        log.info("responseEntity: {}", responseEntity);
        response = responseEntity.getBody();
        return response;
    }

    public ArticleDto readOne(Long id) {
        ResponseEntity<ArticleDto> responseEntity = restClient.get()
                .uri("/articles/{id}", id)
                .retrieve()
                .toEntity(ArticleDto.class);
        log.info("responseEntity, Object...: {}", responseEntity);

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
        responseEntity = restClient.get()
                .uri("/articles/{id}", id)
                .retrieve()
                .toEntity(ArticleDto.class);
        log.info("responseEntity, Map: {}", responseEntity);
        return responseEntity.getBody();
    }

    public List<ArticleDto> readAll() {
        return restClient.get()
                .uri("/articles")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public ArticleDto update(Long id, ArticleDto dto) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);
        ResponseEntity<ArticleDto> responseEntity = restClient.put()
                .uri("/articles/{id}", uriVariables)
                .body(dto)
                .retrieve()
                .toEntity(ArticleDto.class);
        log.info("update responseEntity: {}", responseEntity);
        return responseEntity.getBody();
    }

    public void delete(Long id) {
        ResponseEntity<Void> responseEntity = restClient
                .delete()
                .uri("/articles/{id}", id)
                .retrieve()
                .toBodilessEntity();
        log.info("delete responseEntity: {}", responseEntity);
    }
}
