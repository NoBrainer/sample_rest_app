package com.example.sample_rest_app.mapper;

import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO fromEntity(Person entity);

    Person toEntity(PersonDTO dto);

    List<PersonDTO> fromEntities(List<Person> entities);

    List<Person> toEntities(List<PersonDTO> dtos);
}
