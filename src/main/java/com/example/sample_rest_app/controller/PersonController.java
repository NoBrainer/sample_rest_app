package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.mapper.PersonMapper;
import com.example.sample_rest_app.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/person")
public class PersonController {
    protected final PersonService service;

    protected static final PersonMapper MAPPER = PersonMapper.INSTANCE;

    //TODO: Remove excessive logging after getting the create endpoint to work
    //TODO: It may not work due to the database not being setup?
    //TODO: Try with tests instead?

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO dto) {
        log.debug("create called");
        var entity = MAPPER.toEntity(dto);
        log.debug("entity mapped");
        var savedEntity = service.create(entity);
        log.debug("entity saved id={}", savedEntity.getId());
        return ResponseEntity.ok(MAPPER.fromEntity(savedEntity));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> getAll() {
        log.debug("getAll called");
        var entities = service.getAll();
        log.debug("entities retrieved");
        return ResponseEntity.ok(MAPPER.fromEntities(entities));
    }

    @GetMapping("/paged")
    public ResponseEntity<PageImpl<PersonDTO>> getAll(Pageable pageable) {
        log.debug("getAll called with pageable={}", pageable);
        var page = service.getAll(pageable);
        log.debug("page of entities retrieved");
        var entities = page.getContent();
        var dtos = MAPPER.fromEntities(entities);
        log.debug("{} dtos converted", dtos.size());
        return ResponseEntity.ok(new PageImpl<>(dtos, pageable, page.getTotalElements()));
    }
}
