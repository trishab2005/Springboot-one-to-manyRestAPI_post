package com.example.service;

import com.example.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser( UserDto userDto);
    UserDto getUser( Long id);
    List<UserDto> getAllUsers();
    UserDto updateUser( UserDto userDto, Long id);
    void deleteUser( Long id);

}
