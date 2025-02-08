package com.example.springpostgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.springpostgres.model.GetBookApiResponse;

import reactor.core.publisher.Mono;

@Service
public class ApiBookService {

    @Autowired
    private WebClient fakeApiClient;

    public Mono<GetBookApiResponse> getBookById(Integer id){

        // WebClient webClient = WebClient.builder().baseUrl("https://fakerestapi.azurewebsites.net").build();

        Mono<GetBookApiResponse> response = fakeApiClient.get()
            .uri("/api/v1/Books/{id}", id)
            .retrieve()
            .bodyToMono(GetBookApiResponse.class);

        System.out.println("ini responsenya kan" + response);

        return response;

    }

}
