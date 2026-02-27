package org.example.pizzabackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.*;
import org.example.pizzabackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> findAllWithoutPagination() {
        return userService.findAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<UserResponseDto> findAll(@PageableDefault(page = 0, size = 5) Pageable pageable, UserFilter filter) {
        return userService.findAll(pageable, filter);
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return userService.createUser(createUserRequestDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDto update(@PathVariable Integer id, @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        return userService.updateUser(id, updateUserRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}
