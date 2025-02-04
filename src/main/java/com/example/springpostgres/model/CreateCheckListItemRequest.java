package com.example.springpostgres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCheckListItemRequest {

    @JsonIgnore
    @NotNull
    private Integer checkListId;

    @NotBlank(message = "itemName tidak boleh kosong")
    private String itemName;

}
