package org.example.pizzabackend.dto;

import lombok.Value;
import org.example.pizzabackend.entity.Pizza;
import org.example.pizzabackend.entity.Size;

import java.math.BigDecimal;

@Value
public class PizzaVariantResponseDto {
    Integer id;
    PizzaResponseDto pizza;
    SizeResponseDto size;
    BigDecimal price;
}
