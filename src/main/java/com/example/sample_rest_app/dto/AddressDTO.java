package com.example.sample_rest_app.dto;

import com.example.sample_rest_app.model.Person;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDTO {
    Long id;

    String streetNumber;
    String streetName;
    String apartmentNumber;

    String city;
    String state;
    String zipCode;

    String country;

    @Builder.Default
    List<Person> people = new ArrayList<>();
}
