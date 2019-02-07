package com.juliofalbo.poc.archunit.services;

import com.juliofalbo.poc.archunit.dtos.AppPatchRequestDTO;
import com.juliofalbo.poc.archunit.dtos.AppPostOrPutRequestDTO;
import com.juliofalbo.poc.archunit.dtos.AppResponseDTO;
import com.juliofalbo.poc.archunit.entities.App;
import com.juliofalbo.poc.archunit.exceptions.ResourceNotFoundException;
import com.juliofalbo.poc.archunit.repositories.AppRepository;
import com.juliofalbo.poc.archunit.utils.CustomBeanUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;

    public Page<AppResponseDTO> findAll(@NonNull PageRequest of) {
        return appRepository.findAll(of).map(App::toResponseDTO);
    }

    public AppResponseDTO save(@NonNull AppPostOrPutRequestDTO requestDTO) {
        return appRepository.save(requestDTO.toEntity()).toResponseDTO();
    }

    public AppResponseDTO update(@NonNull String id, @NonNull AppPostOrPutRequestDTO putDTO) {
        appRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Any App with id {"+id+"} was found."));
        return appRepository.save(putDTO.toEntityWithId(id)).toResponseDTO();
    }

    public void delete(@NonNull String id) {
        appRepository.deleteById(id);
    }

    public AppResponseDTO partialUpdate(@NonNull String id, @NonNull AppPatchRequestDTO patchDTO) {
        App dBapp = appRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Any App with id {"+id+"} was found."));
        App newApp = patchDTO.toEntityWithId(id);
        CustomBeanUtils.copyProperties(newApp, dBapp);
        return appRepository.save(dBapp).toResponseDTO();
    }

    public AppResponseDTO findById(@NonNull String id) {
        return appRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Any App with id {"+id+"} was found.")).toResponseDTO();
    }
}
