package com.example.sample_rest_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {
    Long id;

    String streetNumber;
    String streetName;
    String apartmentNumber;

    String city;
    String state;
    String zipCode;

    String country;

    PersonDTO person;
}
