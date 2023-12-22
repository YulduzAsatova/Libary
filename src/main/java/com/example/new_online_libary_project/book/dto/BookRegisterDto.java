package com.example.new_online_libary_project.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRegisterDto {
    private String title;
    private String description;
    private String author;
    private String language;
    private String publisher;
    private String year;
    private Integer pages;
    private MultipartFile cover;
    private MultipartFile file;
}
