package com.example.sample_rest_app.util;

import com.example.sample_rest_app.dto.AddressDTO;
import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.mapper.AddressMapper;
import com.example.sample_rest_app.mapper.PersonMapper;
import com.example.sample_rest_app.model.Address;
import com.example.sample_rest_app.model.Person;

import java.util.UUID;

/**
 * This test data serves as an example for DTOs and entities having all fields set.
 */
public class MockUtil {

    public static final UUID DEFAULT_PERSON_UUID = UUID.randomUUID();
    public static final UUID DEFAULT_ADDRESS_UUID = UUID.randomUUID();

    private static final PersonDTO flatExamplePersonDto = PersonDTO.builder()
            .firstName("Alberta")
            .middleName("Juniper")
            .lastName("Stevens")
            .build();
    private static final AddressDTO flatExampleAddressDto = AddressDTO.builder()
            .streetNumber("123")
            .streetName("Main Street")
            .apartmentNumber("C")
            .city("Frederick")
            .state("MD")
            .zipCode("21702")
            .country("USA")
            .build();

    private static final Person flatExamplePerson = PersonMapper.INSTANCE.toEntity(flatExamplePersonDto);
    private static final Address flatExampleAddress = AddressMapper.INSTANCE.toEntity(flatExampleAddressDto);

    public static PersonDTO mockCreatePersonDto() {
        return flatExamplePersonDto.toBuilder()
                .address(flatExampleAddressDto)
                .build();
    }

    public static PersonDTO mockPersonDto() {
        return mockPersonDto(DEFAULT_PERSON_UUID, DEFAULT_ADDRESS_UUID);
    }

    public static PersonDTO mockPersonDto(UUID personUuid, UUID addressUuid) {
        var person = flatExamplePersonDto.toBuilder().uuid(personUuid).build();
        var address = flatExampleAddressDto.toBuilder()
                .uuid(addressUuid)
                .person(person)
                .build();
        person.setAddress(address);
        return person;
    }

    public static Person mockPerson() {
        return mockPerson(DEFAULT_PERSON_UUID, DEFAULT_ADDRESS_UUID);
    }

    public static Person mockPerson(UUID personUuid, UUID addressUuid) {
        var person = flatExamplePerson.toBuilder().uuid(personUuid).build();
        var address = flatExampleAddress.toBuilder()
                .uuid(addressUuid)
                .person(person)
                .build();
        person.setAddress(address);
        return person;
    }
}
