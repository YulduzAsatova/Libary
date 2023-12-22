package com.example.new_online_libary_project.service;
import com.example.new_online_libary_project.entity.MyBookList;
import com.example.new_online_libary_project.repository.MyBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyBookService {
    private final MyBookRepository myBookRepository;

    public void createMyBook(MyBookList myBookList){
        myBookRepository.save(myBookList);
    }

    public List<MyBookList> getAllMyBookList(){
        return myBookRepository.findAll();
    }
    public void deleteById(Integer id){
        myBookRepository.deleteById(id);
    }
}
