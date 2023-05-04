package com.example.firstwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SignupRequestDto {
    private String username;
    private String password;
    //private String email;
    private boolean admin = false;
    private String adminToken = "";
}