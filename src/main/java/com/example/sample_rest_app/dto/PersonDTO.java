package com.example.sample_rest_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDTO {
    Long id;

    String firstName;
    String middleName;
    String lastName;

    @Builder.Default
    List<AddressDTO> addresses = new ArrayList<>();
}
