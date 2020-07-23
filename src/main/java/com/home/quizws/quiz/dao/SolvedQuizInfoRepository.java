package com.home.quizws.quiz.dao;

import com.home.quizws.quiz.model.SolvedQuizInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizInfoRepository extends PagingAndSortingRepository<SolvedQuizInfo, Long> {

    @Query("select u from SolvedQuizInfo u where u.userName = ?1 ")
    Page<SolvedQuizInfo> findByUsername(String username, Pageable pageable);

}


