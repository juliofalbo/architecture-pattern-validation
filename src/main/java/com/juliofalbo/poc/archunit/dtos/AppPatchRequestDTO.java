package com.juliofalbo.poc.archunit.dtos;

import com.juliofalbo.poc.archunit.entities.App;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class AppPatchRequestDTO {

    @NotNull
    private String information;

    public App toEntityWithId(@NonNull String id){
        return App.builder().id(id).information(this.information).build();
    }
}
