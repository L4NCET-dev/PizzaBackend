package org.example.pizzabackend.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorResponse {
    LocalDateTime timestamp;
    int status;
    String error;
    String message;
    String path;
}
