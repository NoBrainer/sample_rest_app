package com.example.sample_rest_app.service;

import com.example.sample_rest_app.model.Address;
import com.example.sample_rest_app.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService {
    protected final AddressRepository repository;

    @Transactional
    public Address create(Address entity) {
        return repository.save(entity);
    }

    @Transactional
    public List<Address> getAll() {
        return repository.findAll();
    }

    @Transactional
    public PageImpl<Address> getAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
    }
}
