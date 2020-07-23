package com.home.quizws.user.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("User with provided email does not exist : " + email);
    }
}
