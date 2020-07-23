package com.home.quizws.quiz.dao;

import com.home.quizws.quiz.model.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDBRepository extends PagingAndSortingRepository<Quiz, Integer> {
}
