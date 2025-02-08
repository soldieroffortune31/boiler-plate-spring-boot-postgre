package com.example.springpostgres.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCheckListWithItemsRequst {

    @JsonIgnore
    @NotNull
    private Integer penggunaId;

    @NotBlank(message = "name tidak boleh kosong")
    private String name;
    
    @NotEmpty(message = "items todo tidak boleh kosong")
    @Valid
    private List<@Valid CreateCheckListItemWihtoutIdRequest> items;

}
