package org.example.pizzabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.PizzaResponseDto;
import org.example.pizzabackend.dto.PizzaVariantResponseDto;
import org.example.pizzabackend.dto.SizeResponseDto;
import org.example.pizzabackend.entity.Pizza;
import org.example.pizzabackend.entity.PizzaVariant;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PizzaVariantMapper implements Mapper<PizzaVariant, PizzaVariantResponseDto> {

    private final PizzaMapper pizzaMapper;
    private final SizeMapper sizeMapper;


    @Override
    public PizzaVariantResponseDto map(PizzaVariant object) {

        PizzaResponseDto pizza = Optional.ofNullable(object.getPizza())
                .map(objectPizza -> pizzaMapper.map(objectPizza))
                .orElse(null);

        SizeResponseDto size = Optional.ofNullable(object.getSize())
                .map(objectSize -> sizeMapper.map(objectSize))
                .orElse(null);


        return new PizzaVariantResponseDto(
                object.getId(),
                pizza,
                size,
                object.getPrice()

        );
    }
}
