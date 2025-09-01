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

    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO dto) {
        log.debug("called create with {}", dto);
        var entity = MAPPER.toEntity(dto);
        var savedEntity = service.create(entity);
        return ResponseEntity.ok(MAPPER.fromEntity(savedEntity));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> getAll() {
        log.debug("called getAll");
        var entities = service.getAll();
        return ResponseEntity.ok(MAPPER.fromEntities(entities));
    }

    @GetMapping("/paged")
    public ResponseEntity<PageImpl<PersonDTO>> getAll(Pageable pageable) {
        log.debug("called getAll with {}", pageable);
        var page = service.getAll(pageable);
        var entities = page.getContent();
        var dtos = MAPPER.fromEntities(entities);
        return ResponseEntity.ok(new PageImpl<>(dtos, pageable, page.getTotalElements()));
    }
}
