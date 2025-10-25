package com.example.springwebflux_deepdive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;
}
