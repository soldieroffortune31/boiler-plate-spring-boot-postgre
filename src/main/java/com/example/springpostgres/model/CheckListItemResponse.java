package com.example.springpostgres.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckListItemResponse {

    private Integer checkListItemId;

    private String itemName;

    private Boolean statusActive;

}
