package com.example.sample_rest_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "person")
@Jacksonized
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Person {
    @Id
    private Long id;

    String firstName;
    String middleName;
    String lastName;

    @Builder.Default
    @ManyToMany
    List<Address> addresses = new ArrayList<>();
}
