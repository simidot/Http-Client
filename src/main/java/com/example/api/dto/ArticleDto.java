package com.example.api.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//Request Body또는 Response Body를 나타낸다.
// 이걸 HTTP Client들이 그대로 사용 가능.
public class ArticleDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String writer;
}
