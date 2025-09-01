package com.example.sample_rest_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //TODO: switch to UUID
    Long id;

    String firstName;
    String middleName;
    String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    Address address;
}
