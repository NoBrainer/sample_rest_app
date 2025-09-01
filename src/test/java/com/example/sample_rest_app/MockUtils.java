package com.example.sample_rest_app;

import com.example.sample_rest_app.dto.AddressDTO;
import com.example.sample_rest_app.dto.PersonDTO;
import com.example.sample_rest_app.mapper.AddressMapper;
import com.example.sample_rest_app.mapper.PersonMapper;
import com.example.sample_rest_app.model.Address;
import com.example.sample_rest_app.model.Person;

import java.util.List;

/**
 * This test data serves as an example for DTOs and entities having all fields set.
 */
public class MockUtils {

    public static final Long DEFAULT_PERSON_ID = 100L;
    public static final Long DEFAULT_ADDRESS_ID = 200L;

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
                .addresses(List.of(flatExampleAddressDto))
                .build();
    }

    public static AddressDTO mockCreateAddressDto() {
        return flatExampleAddressDto;
    }

    public static PersonDTO mockPersonDto() {
        return mockPersonDto(DEFAULT_PERSON_ID, DEFAULT_ADDRESS_ID);
    }

    public static PersonDTO mockPersonDto(Long personId, Long addressId) {
        var person = flatExamplePersonDto.toBuilder().id(personId).build();
        var address = flatExampleAddressDto.toBuilder()
                .id(addressId)
                .people(List.of(person))
                .build();
        person.setAddresses(List.of(address));
        return person;
    }

    public static AddressDTO mockAddressDto() {
        return mockPersonDto().getAddresses().getFirst();
    }

    public static AddressDTO mockAddressDto(Long personId, Long addressId) {
        return mockPersonDto(personId, addressId).getAddresses().getFirst();
    }

    public static Person mockPerson() {
        return mockPerson(DEFAULT_PERSON_ID, DEFAULT_ADDRESS_ID);
    }

    public static Person mockPerson(Long personId, Long addressId) {
        var person = flatExamplePerson.toBuilder().id(personId).build();
        var address = flatExampleAddress.toBuilder()
                .id(addressId)
                .people(List.of(person))
                .build();
        person.setAddresses(List.of(address));
        return person;
    }

    public static Address mockAddress() {
        return mockPerson().getAddresses().getFirst();
    }

    public static Address mockAddress(Long personId, Long addressId) {
        return mockPerson(personId, addressId).getAddresses().getFirst();
    }
}
