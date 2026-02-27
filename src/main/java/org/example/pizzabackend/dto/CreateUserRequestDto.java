package org.example.pizzabackend.dto;

import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class CreateUserRequestDto {
    String username;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String rawPassword;
}
