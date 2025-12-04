package org.example.pizzabackend.service;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.CreateUserRequestDto;
import org.example.pizzabackend.dto.UpdateUserRequestDto;
import org.example.pizzabackend.dto.UserResponseDto;
import org.example.pizzabackend.mapper.UserMapper;
import org.example.pizzabackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.map(user))
                .toList();
    }

    public Optional<UserResponseDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(user -> userMapper.map(user));
    }

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        return Optional.ofNullable(createUserRequestDto)
                .map(userDto -> userMapper.fromCreateDto(userDto))
                .map(user -> userRepository.save(user))
                .map(user -> userMapper.map(user))
                .orElseThrow();
    }

    @Transactional
    public Optional<UserResponseDto> updateUser(Integer id, UpdateUserRequestDto updateUserRequestDto) {
        return userRepository.findById(id)
                .map(user -> userMapper.fromUpdateDto(updateUserRequestDto, user))
                .map(user -> userRepository.saveAndFlush(user))
                .map(user -> userMapper.map(user));
    }

    @Transactional
    public boolean deleteUser(Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }


}
