package org.example.pizzabackend.dto;

import lombok.Value;
import org.example.pizzabackend.entity.Code;

@Value
public class SizeResponseDto {
    Integer id;
    Code code;
    Integer weight;
}
