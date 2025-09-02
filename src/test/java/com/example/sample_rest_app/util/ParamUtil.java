package com.example.sample_rest_app.util;

import com.example.sample_rest_app.dto.PersonDTO;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class ParamUtil {

    public static ParameterizedTypeReference<List<PersonDTO>> listOfPeopleType() {
        return new ParameterizedTypeReference<>() {
        };
    }
}
