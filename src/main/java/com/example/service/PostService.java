package com.example.service;

import com.example.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto addPost(Long userId, PostDto postDto);

    PostDto getPostById(Long userId, Long postId);

    List<PostDto> getAllPostsByUserId(Long userId);

    PostDto updatePost(Long userId, Long postId, PostDto postDto);

    void deletePost(Long userId, Long postId);
}