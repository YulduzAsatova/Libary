package com.example.new_online_libary_project.controller;

import com.example.new_online_libary_project.service.MyBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MyBookListController {

    private final MyBookService myBookService;
@RequestMapping("/deleteMyBookList/{id}")
    public String deleteMyBook(@PathVariable("id") Integer id){
    myBookService.deleteById(id);
    return "redirect:/book/my_books";
}
}
