package org.example.pizzabackend.exception;

public class UserNotFoundException extends RuntimeException {

    private final Integer id;

    public UserNotFoundException(Integer id) {
        super("User with id " + id + " not found");
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
