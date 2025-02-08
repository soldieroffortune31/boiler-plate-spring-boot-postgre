package com.example.springpostgres.model;

import lombok.Data;

@Data
public class GetBookApiResponse {
    
    private Integer id;

    private String title;

    private String description;

    private Integer pageCount;

    private String excerpt;

    private String publishDate;

}
