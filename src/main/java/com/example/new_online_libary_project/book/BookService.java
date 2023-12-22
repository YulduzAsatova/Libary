package com.example.new_online_libary_project.book;


import com.example.new_online_libary_project.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void create(Book book) {
        bookRepository.save(book);
    }
    @Transactional
    public List<Book> getAllBook(){
        return bookRepository.findAll();
    }
    @Transactional
    public Book getBookById(Integer id){
        return bookRepository.findById(id).orElseThrow();
    }
    @Transactional
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }
}

