package org.example.pizzabackend.mapper;

import org.example.pizzabackend.dto.UserResponseDto;
import org.example.pizzabackend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserResponseDto> {

    @Override
    public UserResponseDto map(User object) {
        return new UserResponseDto(
                object.getId(),
                object.getUsername(),
                object.getRole()
        );
    }
}
