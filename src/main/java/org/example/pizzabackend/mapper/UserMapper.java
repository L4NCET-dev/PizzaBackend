package org.example.pizzabackend.mapper;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.CreateUserRequestDto;
import org.example.pizzabackend.dto.UpdateUserRequestDto;
import org.example.pizzabackend.dto.UserResponseDto;
import org.example.pizzabackend.entity.Role;
import org.example.pizzabackend.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserResponseDto toResponse(User object) {
        return new UserResponseDto(
                object.getId(),
                object.getUsername(),
                object.getRole()
        );
    }

    public User toEntity(CreateUserRequestDto createObject) {
        User user = new User();
        user.setUsername(createObject.getUsername());
        user.setLastName(createObject.getLastName());
        user.setFirstName(createObject.getFirstName());
        user.setBirthDate(createObject.getBirthDate());

        Optional.ofNullable(createObject.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        user.setRole(Role.USER);

        return user;
    }

    public User updateEntity(UpdateUserRequestDto updateObject, User user) {
        user.setLastName(updateObject.getLastName());
        user.setFirstName(updateObject.getFirstName());
        user.setBirthDate(updateObject.getBirthDate());

        return user;
    }
}
