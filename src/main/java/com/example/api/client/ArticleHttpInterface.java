package com.example.api.client;

import com.example.api.dto.ArticleDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange("/articles")
public interface ArticleHttpInterface {
    //CRUD를 할거기때문에 해당하는 메서드를 다 만든다.

    // @<Method>Exchange 어노테이션은 해당 메서드가 실행될 때
    // HTTP Request의 메서드와 Path를 결정한다.

    // Path, Body, (Query) Parameter를 매개변수로
    // 나타내면 HTTP Request에 포함된다.

    //CREATE
    @PostExchange
    ArticleDto create(
            @RequestBody
            ArticleDto dto
    );

    //READ ALL
    @GetExchange
    List<ArticleDto> readAll();

    //READ ONE
    @GetExchange("/{id}")
    ArticleDto readOne(@PathVariable("id")
                       Long id);

    //UPDATE
    @PutExchange("/{id}")
    ArticleDto update(
            @PathVariable("id") Long id,
            @RequestBody ArticleDto dto
    );

    //DELETE
    @DeleteExchange("/{id}")
    void delete(@PathVariable("id") Long id);
}
