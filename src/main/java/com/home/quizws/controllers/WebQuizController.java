package com.home.quizws.controllers;

import com.home.quizws.quiz.QuizService;
import com.home.quizws.quiz.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

@Slf4j
@RestController
public class WebQuizController {

    private final QuizService quizService;

    @Autowired
    public WebQuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("api/quizzes/{id}")
    public QuizWithIdDto quiz(@PathVariable(value = "id") int quizId) {
        return quizService.getQuiz(quizId);
    }

    @GetMapping("api/quizzes")
    public Page<Quiz> getQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            Principal principal) {
        log.info("Invoked metod GET api/quizzes for user : {}", principal.getName());
        return quizService.getQuizess(page, pageSize, sortBy);
    }

    @PostMapping("api/quizzes")
    public QuizWithIdDto addQuizz(@Valid @RequestBody QuizDto quizDto, Principal principal) {
        log.info("passed quiz is = {}", quizDto);
        if (quizDto.getAnswer() == null) {
            quizDto.setAnswer(Collections.emptyList());
        }
        return quizService.addQuiz(quizDto, principal.getName());
    }

    @PostMapping("api/quizzes/{id}/solve")
    public Feedback checkAnswer(@PathVariable(value = "id") int quizId,
                                @RequestBody Answer answer, Principal principal) {
        return quizService.checkAnswer(quizId, answer.getAnswer(), principal.getName());
    }

    @GetMapping("api/quizzes/completed")
    public Page<SolvedQuizInfo> getSolvedQuizesForUser(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "completedAt") String sortBy,
            Principal principal) {
        log.info("Invoked metod GET api/quizzes/completed for user : {}", principal.getName());
        return quizService.getSolvedQuizesForUser(page, pageSize, sortBy, principal.getName());
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable(value = "id") int quizId, Principal principal) {
        quizService.deleteQuiz(quizId, principal.getName());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

