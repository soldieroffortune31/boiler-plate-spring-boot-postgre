package com.example.springpostgres.model;

// import com.example.springpostgres.validation.BooleanValue;
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
public class UpdateCheckListItemRequest {

    @JsonIgnore
    @NotNull
    private Integer checkListId;

    @JsonIgnore
    @NotNull
    private Integer checkListItemId;

    @JsonIgnore
    @NotNull
    private Integer penggunaId;

    @NotBlank(message = "itemName tidak boleh kosong")
    @Size(max = 100)
    private String itemName;

    //validasi boolean mungkin bisa dilakukan di level service, karena di controller udh dianggap error jika valunya tidak sesuai
    // @BooleanValue(message = "statusActice harus bernilai true atau false")
    @NotNull
    private Boolean statusActive;

}
