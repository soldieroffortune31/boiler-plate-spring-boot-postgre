package com.example.springpostgres.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springpostgres.model.WebResponse;
import com.example.springpostgres.security.CustomWebAuthenticationDetails;

@RestController
public class TodoController {

    @GetMapping(
        path = "hello"
    )
    public WebResponse<String> getHello(){

        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String message = "Hello " + details.getUsername() + " with id " + details.getPenggunaId();

        return WebResponse.<String>builder().code(200).message(message).build();
    }


}
