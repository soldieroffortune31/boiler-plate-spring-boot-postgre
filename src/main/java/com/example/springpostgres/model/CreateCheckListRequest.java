package com.example.springpostgres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCheckListRequest {

    @NotBlank(message = "nama todo tidak boleh kosong")
    @Size(max = 100)
    private String name;

    @JsonIgnore
    @NotNull
    private Integer penggunaId;

}
