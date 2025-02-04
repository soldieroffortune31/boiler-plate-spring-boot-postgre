package com.example.springpostgres.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @NotBlank(message = "username tidak boleh kosong")
    private String username;

    @NotBlank(message = "username tidak boleh kosong")
    private String password;

}
