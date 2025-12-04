package org.example.pizzabackend.mapper;

import org.example.pizzabackend.dto.CreateUserRequestDto;
import org.example.pizzabackend.dto.UpdateUserRequestDto;
import org.example.pizzabackend.dto.UserResponseDto;
import org.example.pizzabackend.entity.Role;
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

    public User fromCreateDto(CreateUserRequestDto createUserRequestDto) {
        User user = new User();
        user.setUsername(createUserRequestDto.getUsername());
        user.setLastName(createUserRequestDto.getLastName());
        user.setFirstName(createUserRequestDto.getFirstName());
        user.setBirthDate(createUserRequestDto.getBirthDate());
        user.setRole(Role.USER);

        return user;
    }

    public User fromUpdateDto(UpdateUserRequestDto updateUserRequestDto, User user) {
        user.setLastName(updateUserRequestDto.getLastName());
        user.setFirstName(updateUserRequestDto.getFirstName());
        user.setBirthDate(updateUserRequestDto.getBirthDate());

        return user;
    }
}
