package com.example.springpostgres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springpostgres.model.CreatePenggunaRequest;
import com.example.springpostgres.model.WebResponse;
import com.example.springpostgres.service.PenggunaService;

@RestController
public class PenggunaController {

    @Autowired
    private PenggunaService penggunaService;

    @PostMapping(
        path = "/register",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody CreatePenggunaRequest request){

        System.out.println("request : " + request.toString());

        penggunaService.resigter(request);
        return WebResponse.<String>builder().code(HttpStatus.OK.value()).message("Ok").build();
    }

}
