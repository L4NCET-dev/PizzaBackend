package org.example.pizzabackend.dto;

import lombok.Value;

import java.util.List;

@Value
public class PageResponseDto<T> {
    List<T> content;
    int page;
    int size;
    long totalElements;
    int totalPages;
    boolean first;
    boolean last;
}
