package org.example.pizzabackend.service;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.*;
import org.example.pizzabackend.entity.User;
import org.example.pizzabackend.mapper.UserMapper;
import org.example.pizzabackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements  UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toResponse(user))
                .toList();
    }

    public PageResponse<UserResponseDto> findAll(Pageable pageable, UserFilter filter) {
        Page<User> page = userRepository.findAll(pageable);

        List<UserResponseDto> content = page.getContent().stream()
                .map(user -> userMapper.toResponse(user))
                .toList();

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public Optional<UserResponseDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(user -> userMapper.toResponse(user));
    }

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
        return Optional.ofNullable(createUserRequestDto)
                .map(userDto -> userMapper.toEntity(userDto))
                .map(user -> userRepository.save(user))
                .map(user -> userMapper.toResponse(user))
                .orElseThrow();
    }

    @Transactional
    public Optional<UserResponseDto> updateUser(Integer id, UpdateUserRequestDto updateUserRequestDto) {
        return userRepository.findById(id)
                .map(user -> userMapper.updateEntity(updateUserRequestDto, user))
                .map(user -> userRepository.saveAndFlush(user))
                .map(user -> userMapper.toResponse(user));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s not found".formatted(username)));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

}
