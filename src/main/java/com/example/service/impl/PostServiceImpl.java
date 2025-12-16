package com.example.service.impl;

import com.example.dto.PostDto;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.PostRepository;
import com.example.repository.UserRepository;
import com.example.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public PostDto addPost(Long userId, PostDto postDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        PostDto savedPostDto = modelMapper.map(savedPost, PostDto.class);
        savedPostDto.setUserId(savedPost.getUser().getId());

        return savedPostDto;
    }

    @Override
    public PostDto getPostById(Long userId, Long postId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        if(!post.getUser().getId().equals(user.getId())){
            throw new ResourceNotFoundException("This post does not belong to the user with id " + userId);
        }

        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setUserId(post.getUser().getId());

        return postDto;
    }

    @Override
    public List<PostDto> getAllPostsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Post> posts = postRepository.findByUserId(userId);

        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(Long userId, Long postId, PostDto postDto) {

        // check if user exists in the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));

        // retrieve the post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));


        if (!post.getUser().getId().equals(user.getId())){
            throw new BadRequestException("This post does not belong to the user with id " + userId);
        }

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost =  postRepository.save(post); // update
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Long userId, Long postId) {
        // check if user exists in the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));

        // retrieve the post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));


        if (!post.getUser().getId().equals(user.getId())){
            throw new BadRequestException("This post does not belong to the user with id " + userId);
        }

        postRepository.delete(post);
    }
}