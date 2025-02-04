package com.example.springpostgres.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebResponse<T> {
    
    private Integer code;

    private String message;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer limit;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer offset;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

}
