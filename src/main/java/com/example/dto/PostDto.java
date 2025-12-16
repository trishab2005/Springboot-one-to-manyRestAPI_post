package com.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private Long userId;


}