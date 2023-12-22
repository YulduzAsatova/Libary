package com.example.new_online_libary_project.user.dto;

import com.example.new_online_libary_project.user.entity.Role;
import com.example.new_online_libary_project.user.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private String password;
    private Role role;
    private Status status;
    private LocalDateTime createAd;
    private LocalDateTime updateAd;
}
