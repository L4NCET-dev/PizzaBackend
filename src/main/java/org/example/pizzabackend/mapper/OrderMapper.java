package org.example.pizzabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.OrderResponseDto;
import org.example.pizzabackend.dto.PizzaVariantResponseDto;
import org.example.pizzabackend.dto.UserResponseDto;
import org.example.pizzabackend.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderMapper implements Mapper<Order, OrderResponseDto> {

    private final UserMapper userMapper;
    private final PizzaVariantMapper pizzaVariantMapper;

    @Override
    public OrderResponseDto map(Order object) {

        UserResponseDto user = Optional.ofNullable(object.getUser())
                .map(userObject -> userMapper.map(userObject))
                .orElse(null);

        PizzaVariantResponseDto pizzaVariant = Optional.ofNullable(object.getPizzaVariant())
                .map(pizzaVariantObject -> pizzaVariantMapper.map(pizzaVariantObject))
                .orElse(null);

        return new OrderResponseDto(
                object.getId(),
                user,
                pizzaVariant
        );
    }
}
