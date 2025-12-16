package com.example.controller;

import com.example.dto.PostDto;
import com.example.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

@AllArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostDto> addPost(@PathVariable Long userId,
                                           @RequestBody PostDto postDto){
        PostDto savedPost = postService.addPost(userId, postDto);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long userId,
                                               @PathVariable("id") Long postId){
        PostDto postDto = postService.getPostById(userId, postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostsByUserId(@PathVariable Long userId){
        List<PostDto> posts = postService.getAllPostsByUserId(userId);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{userId}/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long userId,
                                              @PathVariable("id") Long postId,
                                              @RequestBody PostDto postDto){

        PostDto updatedPost = postService.updatePost(userId, postId, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("{userId}/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long userId,
                                             @PathVariable("id") Long postId){
        postService.deletePost(userId, postId);
        return ResponseEntity.ok("Post deleted successfully!");
    }
}