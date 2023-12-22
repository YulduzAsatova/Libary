package com.example.new_online_libary_project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoginDto {
    private String phoneNumber;
    private String password;
}
