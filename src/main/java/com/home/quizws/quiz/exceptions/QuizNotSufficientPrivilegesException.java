package com.home.quizws.quiz.exceptions;

public class QuizNotSufficientPrivilegesException extends RuntimeException {

    public QuizNotSufficientPrivilegesException() {
        super("You dont have sufficient privileges to execute this action ");
    }

}
