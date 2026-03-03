package org.example.pizzabackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.pizzabackend.config.security.jwt.JwtService;
import org.example.pizzabackend.dto.*;
import org.example.pizzabackend.entity.User;
import org.example.pizzabackend.exception.UserNotFoundException;
import org.example.pizzabackend.mapper.UserMapper;
import org.example.pizzabackend.repository.UserRepository;
import org.example.pizzabackend.repository.specification.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toResponse(user))
                .toList();
    }

    public PageResponse<UserResponseDto> findAll(Pageable pageable, UserFilter filter) {

        var spec = UserSpecification.userFilter(filter);

        Page<User> page = userRepository.findAll(spec, pageable);

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

    public UserResponseDto findById(Integer id) {

        log.debug("Request to find user by id={}", id);

        return userRepository.findById(id)
                .map(user -> userMapper.toResponse(user))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {

        log.info("Creating user with username={}", createUserRequestDto.getUsername());

        return Optional.ofNullable(createUserRequestDto)
                .map(userDto -> userMapper.toEntity(userDto))
                .map(user -> userRepository.save(user))
                .map(user -> userMapper.toResponse(user))
                .orElseThrow();
    }

    @Transactional
    public UserResponseDto updateUser(Integer id, UpdateUserRequestDto updateUserRequestDto) {

        log.info("Updating user id={}", id);

        return userRepository.findById(id)
                .map(user -> userMapper.updateEntity(updateUserRequestDto, user))
                .map(user -> userRepository.saveAndFlush(user))
                .map(user -> userMapper.toResponse(user))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void deleteUser(Integer id) {

        log.warn("Deleting user id={}", id);

        userRepository.findById(id)
                .ifPresentOrElse(user -> userRepository.delete(user),
                        () -> {
                            throw new UserNotFoundException(id);
                        }
                );
    }

    public JwtAuthenticationDto signIn(UserCredentialsDto credentials) {

        log.info("User sign-in attempt: username={}", credentials.getUsername());

        // 1. достаём пользователя (через уже реализованный loadUserByUsername)
        UserDetails userDetails = loadUserByUsername(credentials.getUsername());

        // 2. сверяем пароль
        if (!passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())) {
            // Важно: кидаем BadCredentialsException — её легко обработать в GlobalExceptionHandler
            throw new org.springframework.security.authentication.BadCredentialsException(
                    "Invalid username or password"
            );
        }

        // 3. генерируем пару токенов
        return jwtService.generateAuthToken(userDetails.getUsername());
    }

    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        log.info("Refresh token attempt");

        // 1. проверяем refresh токен
        if (!jwtService.validateJwtToken(refreshToken)) {
            throw new org.springframework.security.authentication.BadCredentialsException(
                    "Invalid refresh token"
            );
        }

        // 2. достаём username из refresh
        String username = jwtService.getUsernameFromToken(refreshToken);

        // 3. генерим новый access, refresh оставляем прежним
        return jwtService.refreshBaseToken(username, refreshToken);
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
