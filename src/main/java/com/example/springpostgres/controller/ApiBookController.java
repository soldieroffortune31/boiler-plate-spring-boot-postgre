package com.example.springpostgres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.springpostgres.model.GetBookApiResponse;
import com.example.springpostgres.service.ApiBookService;

import reactor.core.publisher.Mono;

@RestController
public class ApiBookController {

    @Autowired
    private ApiBookService apiBookService;

    @GetMapping(
        path = "/v1/books/{id}"
    )
    public Mono<GetBookApiResponse> getBookById(@PathVariable Integer id){
        System.out.println("ini id nya " + id);
        return apiBookService.getBookById(id);
    }

}
