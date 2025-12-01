package org.example.pizzabackend.dto;

import lombok.Value;

@Value
public class OrderResponseDto {
    Integer id;
    UserResponseDto user;
    PizzaVariantResponseDto pizzaVariant;
}
