package com.example.new_online_libary_project.book.entity;

import com.example.new_online_libary_project.upload.entity.Upload;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String language;
    @Column(nullable = false)
    private String publisher;
    private String year;
    private Integer pages;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Upload cover;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Upload file;
    @CreationTimestamp
    @Column(columnDefinition = "timeStamp default now()", updatable = false)
    private LocalDateTime createAd;
    private LocalDateTime updateAd;


    

}
