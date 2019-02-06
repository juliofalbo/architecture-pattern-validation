package com.juliofalbo.poc.archunit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppResponseDTO {

    private String id;
    private String information;
    private String emailCreator;

}
