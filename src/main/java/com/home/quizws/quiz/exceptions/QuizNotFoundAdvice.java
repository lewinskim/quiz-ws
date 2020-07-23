package com.home.quizws.quiz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuizNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String quizNotFoundHandler(QuizNotFoundException exception) {
        return exception.getMessage();
    }


    @ExceptionHandler(QuizNotSufficientPrivilegesException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String quizWrongPrivilegesHandler(QuizNotSufficientPrivilegesException exception) {
        return exception.getMessage();
    }
}
