package com.example.sample_rest_app.mapper;

import com.example.sample_rest_app.dto.AddressDTO;
import com.example.sample_rest_app.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO fromEntity(Address entity);

    Address toEntity(AddressDTO dto);

    List<AddressDTO> fromEntities(List<Address> entities);

    List<Address> toEntities(List<AddressDTO> dtos);
}
