package com.example.api;

import com.example.api.client.ArticleRestClient;
import com.example.api.client.ArticleService;
import com.example.api.client.ArticleTemplateClient;
import com.example.api.client.ArticleWebClient;
import com.example.api.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleTemplateClient templateClient;
    private final ArticleWebClient webClient;
    private final ArticleRestClient restClient;
    private final ArticleService articleService;

    @PostMapping
    public ArticleDto create(
            @RequestBody
            ArticleDto dto) {
//        return templateClient.create(dto);
//        return webClient.create(dto);
//        return restClient.create(dto);
        return articleService.create(dto);
    }

    @GetMapping("/{id}")
    public ArticleDto readOne(@PathVariable("id") Long id) {
//        return templateClient.readOne(id);
//        return webClient.readOne(id);
//        return restClient.readOne(id);
        return articleService.readOne(id);
    }


    @GetMapping()
    public List<ArticleDto> readAll() {
//        return templateClient.readAll();
//        return webClient.readAll();
//        return restClient.readAll();
        return articleService.readAll();
    }

    @PutMapping("/{id}")
    public ArticleDto update(@PathVariable("id") Long id,
                             @RequestBody ArticleDto dto) {
//        return templateClient.update(id, dto);
//        return webClient.update(id, dto);
//        return restClient.update(id, dto);
        return articleService.update(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
//        templateClient.delete(id);
//        webClient.delete(id);
//        restClient.delete(id);
        articleService.delete(id);
    }

    @GetMapping("/test")
    public void test() {
        templateClient.readAll();
    }
}
