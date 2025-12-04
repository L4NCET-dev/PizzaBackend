package org.example.pizzabackend.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class UpdateUserRequestDto {
    String firstName;
    String lastName;
    LocalDate birthDate;
}
