package com.example.new_online_libary_project.upload;
import com.example.new_online_libary_project.upload.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository<Upload,Integer> {
}
