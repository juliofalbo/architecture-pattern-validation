package com.juliofalbo.poc.archunit.resources;

import java.net.URI;

import javax.validation.Valid;

import com.juliofalbo.poc.archunit.dtos.AppPatchRequestDTO;
import com.juliofalbo.poc.archunit.dtos.AppPostOrPutRequestDTO;
import com.juliofalbo.poc.archunit.dtos.AppResponseDTO;
import com.juliofalbo.poc.archunit.exceptions.ValidationApiException;
import com.juliofalbo.poc.archunit.services.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/apps")
public class AppResource {

    @Autowired
    private AppService appService;

    @PostMapping
    public ResponseEntity<AppResponseDTO> create(@Valid @RequestBody AppPostOrPutRequestDTO requestDTO, BindingResult result) {
        log.info("M=create, RequestDTO {}", requestDTO);

        if (result.hasErrors()) {
            log.error("Validation error: {}", result.getAllErrors());
            throw new ValidationApiException(result.getAllErrors().stream().map(ObjectError::toString).reduce((o, o2) -> o + " / " + o2).get());
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appService.save(requestDTO).getId()).toUri();

        System.out.println("teste");
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<AppResponseDTO>> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(appService.findAll(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponseDTO> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(appService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppResponseDTO> update(@PathVariable("id") String id, @Valid @RequestBody AppPostOrPutRequestDTO putDTO) {
        return ResponseEntity.ok(appService.update(id, putDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppResponseDTO> partialUpdate(@PathVariable("id") String id, @Valid @RequestBody AppPatchRequestDTO patchDTO) {
        return ResponseEntity.ok(appService.partialUpdate(id, patchDTO));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        appService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
