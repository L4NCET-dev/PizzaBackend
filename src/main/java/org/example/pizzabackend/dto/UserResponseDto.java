package org.example.pizzabackend.dto;

import lombok.Value;
import org.example.pizzabackend.entity.Role;

@Value
public class UserResponseDto {
    Integer id;
    String username;
    Role role;
}
