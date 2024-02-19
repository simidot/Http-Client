package com.example.api.client;

import com.example.api.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleTemplateClient {
    private final RestTemplate restTemplate;

    //GET
    public ArticleDto readOne(Long id) {
        //getForObject =객체를 받기위해 GET요청을 한다.
        ArticleDto response =
                restTemplate.getForObject(String.format("/articles/%d", id),
                        ArticleDto.class);
        log.info("response: {}", response);


        //getForEntity: ResponseEntity를 받기위해 GET요청을 보낸다.
        ResponseEntity<ArticleDto> responseEntity =
                restTemplate.getForEntity(String.format("/articles/%d", id),
                        ArticleDto.class);
        log.info("responseEntity: {}", responseEntity);
        log.info("status code: {}", responseEntity.getStatusCode());
        log.info("headers: {}", responseEntity.getHeaders());
        log.info("body: {}", responseEntity.getBody());

        //getForObject- object
        Object responseObject = restTemplate.getForObject(
                String.format("/articles/%d", id),
                Object.class
        );
        log.info("responseObject: {}", responseObject.getClass());

        response = responseEntity.getBody();
        return response;
    }

    //readAll
    public List<ArticleDto> readAll() {
        //getForObject
        ArticleDto[] response =
                restTemplate.getForObject("/articles",
                        ArticleDto[].class);
        log.info("response type: {}", response.getClass());
//
//        //getForEntity
//        ResponseEntity<ArticleDto[]> responseEntity =
//                restTemplate.getForEntity("/articles", ArticleDto[].class);
//        log.info("responseEntity: {}", responseEntity);
//        log.info("status code: {}", responseEntity.getStatusCode());
//
//        // exchange : 일반적인 상황에서 HTTP요청의 모든 것(메서드, 헤더, 바디 등)을
//        // 묘사하여 요청하기 위한 메서드
//        // +ParamterizedTypeReference<T>를 사용하면 List로 반환한다.
//        ResponseEntity<List<ArticleDto>> responseListEntity =
//                restTemplate.exchange(
//                        //String url
//                        "/articles",
//                        // HttpMethod method
//                        HttpMethod.GET,
//                        // @Nullable HttpEntity<?> requestEntity
//                        null,
//                        // Class<T> responseType
//                        new ParameterizedTypeReference<>() {
//                        }
//                );
//        log.info("response parameterized: {}", responseListEntity.getBody().getClass());
//
//        // getForObject - Object
//        Object responseObject = restTemplate.getForObject("/articles", Object.class);
//        log.info("responseObject : {}", responseObject.getClass());

// 1. URL 인자 대체하기, 가변갯수인자
        Object responsePage = restTemplate.getForObject(
                // String url
                "/articles/paged?page={page}&limit={limit}",
                // Class<T> responseType
                Object.class,
                // Object ... uriVariables
                0, //page
                5 //limit
        );
        log.info("response object page 가변인자: {}", responsePage);

// 2. URL 인자 대체하기, Map<String, Object>
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("page", 0);
        uriVariables.put("limit", 5);
        responsePage = restTemplate.getForObject(
                "/articles/paged?page={page}&limit={limit}",
                Object.class,
                uriVariables
        );
        log.info("response object page Map으로 설정: {}", responsePage);


        String url = UriComponentsBuilder.fromUriString("/articles/paged")
                .queryParam("page", 0)
                .queryParam("limit", 5)
                .toUriString();
        log.info(url);

        log.info("UriComponentsBuilder 사용: "+UriComponentsBuilder.fromUriString("/articles")
                .queryParam("page", 0)
                .queryParam("limit", 5)
                .toUriString());


        // /test?foo=%25%26 -> /test?foo=%2525%2526
        log.info(UriComponentsBuilder.fromUriString("/articles/test")
                .queryParam("foo", "%&")
                .toUriString());
        // /test?foo=%& -> /test?foo=%25%26
        log.info(UriComponentsBuilder.fromUriString("/test")
                .queryParam("foo", "%&")
                .build(false)
                .toUriString());

        //array를 list로 변환
//        return List.of(response);
        return Arrays.stream(response).toList();
    }


    public ArticleDto create(ArticleDto dto) {
        // postForObject : 객체를 받기위해 POST 요청을 한다.

        ArticleDto response =
                restTemplate.postForObject(
                        //1.요청 url
                        "/articles",
                        //2.RequestBody,
                        dto,
                        //  3.Response Body의 Type
                        ArticleDto.class);
        log.info("response: {}", response);

        //postForEntity : ResponseEntity를 받기 위해 POST 요청을 한다.
        ResponseEntity<ArticleDto> responseEntity =
                restTemplate.postForEntity("/articles",
                dto,
                ArticleDto.class);
        log.info("responseEntity: {}", responseEntity);
        log.info("status code: {}", responseEntity.getStatusCode());
        log.info("headers: {}", responseEntity.getHeaders());
        log.info("body: {}", responseEntity.getBody());

        response = responseEntity.getBody();
        return response;
    }

    // PUT
    public ArticleDto update(Long id, ArticleDto dto) {
//        log.info("before update:: {}", dto.toString());
//        restTemplate.put(String.format("/articles/%d", id),
//                dto);
//        log.info("updated:: {}", dto.toString());
//
        //exchange
        ResponseEntity<ArticleDto> responseEntity =
                restTemplate.exchange(
                        // String url
                        String.format("/articles/%d", id),
                        // HttpMethod method
                        HttpMethod.PUT,
                        // HttpEntity<?> requestEntity
                        new HttpEntity<>(dto),
                        // Class<T> responseType
                        ArticleDto.class);
        log.info("status code: {}", responseEntity.getStatusCode());
        log.info("updated:: {}", responseEntity.getBody());

        dto = responseEntity.getBody();
        return dto;
    }

    //DELETE
    public void delete(Long id) {
//        restTemplate.delete(String.format("/articles/%d", id));
        // exchange
        // ResponseEntity<Void>: Response Body가 비어있는 응답
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                String.format("/articles/%d", id),
                HttpMethod.DELETE,
                null,
                Void.class
        );
        //response는 article에서 보내서 받은 응답을 받아오는 것임.
        log.info("status code: {}", responseEntity.getStatusCode());
    }


}
