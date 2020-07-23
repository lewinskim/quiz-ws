package com.home.quizws.user.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("User with provided email already exists : " + email);
    }
}
