package com.example.springpostgres.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePenggunaRequest {

    @NotBlank(message = "email tidak boleh kosong")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "username tidak boleh kosong")
    @Size(max = 50)
    private String username;

    @NotBlank(message = "password tidak boleh kosong")
    @Size(max = 100)
    private String password;

}
