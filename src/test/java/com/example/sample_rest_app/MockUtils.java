package com.example.sample_rest_app;

import com.example.sample_rest_app.dto.AddressDTO;
import com.example.sample_rest_app.dto.PersonDTO;

import java.util.List;

public class MockUtils {

    /**
     * Basis for most test data
     */
    private static final PersonDTO flatExamplePersonDto = PersonDTO.builder()
            .firstName("Alberta")
            .middleName("Juniper")
            .lastName("Stevens")
            .build();

    /**
     * Basis for most test data
     */
    private static final AddressDTO flatExampleAddressDto = AddressDTO.builder()
            .streetNumber("123")
            .streetName("Main Street")
            .apartmentNumber("C")
            .city("Frederick")
            .state("MD")
            .zipCode("21702")
            .country("USA")
            .build();

    /**
     * All fields are set that can be used to create a Person with an Address.
     */
    public static PersonDTO mockCreatePersonDto() {
        return flatExamplePersonDto.toBuilder()
                .addresses(List.of(flatExampleAddressDto))
                .build();
    }

    /**
     * All fields are set that can be used to create an Address without any linked people.
     */
    public static AddressDTO mockCreateAddressDto() {
        return flatExampleAddressDto;
    }

    /**
     * All fields are set, and the Person is linked to one Address.
     */
    public static PersonDTO mockPersonDto() {
        var person = flatExamplePersonDto.toBuilder().id(100L).build();
        var address = flatExampleAddressDto.toBuilder()
                .id(200L)
                .people(List.of(person))
                .build();
        person.setAddresses(List.of(address));
        return person;
    }

    /**
     * All fields are set, and the Address is linked to one Person.
     */
    public static AddressDTO mockAddressDto() {
        return mockPersonDto().getAddresses().getFirst();
    }
}
