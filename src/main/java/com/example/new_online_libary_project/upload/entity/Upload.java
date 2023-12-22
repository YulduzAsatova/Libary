package com.example.new_online_libary_project.upload.entity;

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
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String generatedName;
    @Column(nullable = false)
    private String originalName;
    @Column(nullable = false)
    private String mimeType;
    private long size;
    @Column(nullable = false)
    private String extension;
    @CreationTimestamp
    @Column(columnDefinition = "timeStamp default now()", updatable = false)
    private LocalDateTime createAd;
    private LocalDateTime updateAd;
}