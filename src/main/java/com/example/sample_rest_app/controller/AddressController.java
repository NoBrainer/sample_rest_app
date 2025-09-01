package com.example.sample_rest_app.controller;

import com.example.sample_rest_app.dto.AddressDTO;
import com.example.sample_rest_app.mapper.AddressMapper;
import com.example.sample_rest_app.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/address")
public class AddressController {
    protected final AddressService service;

    protected static final AddressMapper MAPPER = AddressMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<AddressDTO> create(@RequestBody AddressDTO dto) {
        var entity = MAPPER.toEntity(dto);
        var savedEntity = service.create(entity);
        return ResponseEntity.ok(MAPPER.fromEntity(savedEntity));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDTO>> getAll() {
        var entities = service.getAll();
        return ResponseEntity.ok(MAPPER.fromEntities(entities));
    }

    @GetMapping("/paged")
    public ResponseEntity<PageImpl<AddressDTO>> getAll(Pageable pageable) {
        var page = service.getAll(pageable);
        var entities = page.getContent();
        var dtos = MAPPER.fromEntities(entities);
        return ResponseEntity.ok(new PageImpl<>(dtos, pageable, page.getTotalElements()));
    }
}
