package com.example.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
