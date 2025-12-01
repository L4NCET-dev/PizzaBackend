package org.example.pizzabackend.mapper;

import org.example.pizzabackend.dto.PizzaResponseDto;
import org.example.pizzabackend.entity.Pizza;
import org.springframework.stereotype.Component;

@Component
public class PizzaMapper implements Mapper<Pizza, PizzaResponseDto> {

    @Override
    public PizzaResponseDto map(Pizza object) {
        return new PizzaResponseDto(
                object.getId(),
                object.getName()
        );
    }
}
