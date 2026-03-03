package org.example.pizzabackend.dto;

import lombok.Value;

@Value
public class JwtAuthenticationDto {
    String token;
    String refreshToken;
}
