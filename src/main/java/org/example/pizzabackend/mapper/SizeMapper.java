package org.example.pizzabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.SizeResponseDto;
import org.example.pizzabackend.entity.Size;
import org.springframework.stereotype.Component;

@Component
public class SizeMapper implements Mapper<Size, SizeResponseDto> {

    @Override
    public SizeResponseDto map(Size object) {
        return new SizeResponseDto(
                object.getId(),
                object.getCode(),
                object.getWeight()
        );
    }
}
