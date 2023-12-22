package com.example.new_online_libary_project.upload;


import com.example.new_online_libary_project.upload.entity.Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final UploadRepository uploadRepository;

    @Transactional
    public void save(Upload upload) {
        uploadRepository.save(upload);
    }
    @Transactional
    public List<Upload> getAllUpload(){
        return uploadRepository.findAll();
    }
    @Transactional
    public Upload getUploadById(Integer id){
        return uploadRepository.findById(id).orElseThrow();
    }
    @Transactional
    public void deleteById(Integer id) {
        uploadRepository.deleteById(id);
    }
}
