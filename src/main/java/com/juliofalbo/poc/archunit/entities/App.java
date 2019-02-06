package com.juliofalbo.poc.archunit.entities;

import com.juliofalbo.poc.archunit.dtos.AppResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App {

    @Id
    private String id;

    private String information;

    private String emailCreator;

    public AppResponseDTO toResponseDTO(){
        return AppResponseDTO.builder().emailCreator(this.emailCreator).information(this.information).build();
    }

}
