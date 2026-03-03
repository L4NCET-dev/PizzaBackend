package org.example.pizzabackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.pizzabackend.dto.JwtAuthenticationDto;
import org.example.pizzabackend.dto.RefreshTokenDto;
import org.example.pizzabackend.dto.UserCredentialsDto;
import org.example.pizzabackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationDto signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        return userService.signIn(userCredentialsDto);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return userService.refreshToken(refreshTokenDto);
    }
}
