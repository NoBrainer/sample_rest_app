package com.example.sample_rest_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Jacksonized
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //TODO: switch to UUID
    Long id;

    String streetNumber;
    String streetName;
    String apartmentNumber;

    String city;
    String state;
    String zipCode;

    String country;

    @Builder.Default
    @ManyToMany
    List<Person> people = new ArrayList<>();
}
