package com.example.springpostgres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springpostgres.model.LoginRequest;
import com.example.springpostgres.model.LoginResponse;
import com.example.springpostgres.model.WebResponse;
import com.example.springpostgres.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;        

    @PostMapping(
        path = "/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<LoginResponse> login(@RequestBody LoginRequest request){

        LoginResponse loginResponse = authService.login(request);
        return WebResponse.<LoginResponse>builder().code(HttpStatus.OK.value()).message("Ok").data(loginResponse).build();

    }

}
