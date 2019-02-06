package com.juliofalbo.poc.archunit.dtos;

import com.juliofalbo.poc.archunit.entities.App;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AppPostOrPutRequestDTO {

    @NotNull
    private String information;

    @Email
    private String emailCreator;

    public App toEntity(){
        return App.builder().emailCreator(this.emailCreator).information(this.information).build();
    }

    public App toEntityWithId(@NonNull String id){
        return App.builder().id(id).emailCreator(this.emailCreator).information(this.information).build();
    }
}
