package com.example.sample_rest_app.dto;

import com.example.sample_rest_app.model.Address;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonDTO {
    Long id;

    String firstName;
    String middleName;
    String lastName;

    @Builder.Default
    List<Address> addresses = new ArrayList<>();
}
