package com.example.new_online_libary_project.entity;

import com.example.new_online_libary_project.upload.entity.Upload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "myBooks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyBookList {
    @Id
    @Column(name = "book_id")
    private Integer id;
    private String title;
    private String description;
    private String author;
    private String language;
    private String publisher;
    private String year;
    private Integer pages;
    @OneToOne
    private Upload file;
    @OneToOne
    private Upload cover;
    private LocalDateTime createAd;
    private LocalDateTime updateAd;
}
