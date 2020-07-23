package com.home.quizws.quiz;

import com.home.quizws.quiz.dao.FeedbackDao;
import com.home.quizws.quiz.dao.QuizDBRepository;
import com.home.quizws.quiz.dao.SolvedQuizInfoRepository;
import com.home.quizws.quiz.exceptions.QuizNotFoundException;
import com.home.quizws.quiz.exceptions.QuizNotSufficientPrivilegesException;
import com.home.quizws.quiz.model.*;
import com.home.quizws.user.dao.UserRepository;
import com.home.quizws.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuizService {


    private final QuizDBRepository quizDBRepository;
    private final FeedbackDao feedbackDao;
    private final SolvedQuizInfoRepository solvedQuizInfoRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuizService(QuizDBRepository quizDBRepository, FeedbackDao feedbackDao, SolvedQuizInfoRepository solvedQuizInfoRepository, UserRepository userRepository) {
        this.quizDBRepository = quizDBRepository;
        this.feedbackDao = feedbackDao;
        this.solvedQuizInfoRepository = solvedQuizInfoRepository;
        this.userRepository = userRepository;
    }

    public QuizWithIdDto getQuiz(int id) {
        Quiz quiz = getQuizById(id);
        return new QuizWithIdDto().toQuiz(quiz);
    }

    public QuizWithIdDto addQuiz(QuizDto quizDto, String userMail) {
        Quiz quiz = quizDto.toQuiz(userMail);
        Quiz savedQuiz = quizDBRepository.save(quiz);
        return new QuizWithIdDto().toQuiz(savedQuiz);
    }

    public Page<Quiz> getQuizess(Integer pageNo, Integer pageSize, String sortBy) {
        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Quiz> pagedResult = quizDBRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult;
        } else
            return new PageImpl<>(new ArrayList<>());
    }

    public Page<SolvedQuizInfo> getSolvedQuizesForUser(Integer page, Integer pageSize, String sortBy, String userName) {
        PageRequest paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        Page<SolvedQuizInfo> solvedQuizesByUser = solvedQuizInfoRepository.findByUsername(userName, paging);

        if (solvedQuizesByUser.hasContent()) {
            User byEmail = userRepository.findByEmail(userName);
            log.info("Passed user id = {}", byEmail.getId());
            return solvedQuizesByUser;
        } else
            return new PageImpl<>(new ArrayList<>());
    }

    public Feedback checkAnswer(int quizId, List<Integer> answers, String userName) {
        Quiz quiz = getQuizById(quizId);
        List<QuizAnswer> quizAnswers = quiz.getAnswer();
        List<Integer> answersIds = quizAnswers.stream()
                .map(QuizAnswer::getAnswer)
                .collect(Collectors.toList());
        if (answers.containsAll(answersIds) && answersIds.containsAll(answers)) {
            SolvedQuizInfo cpq = new SolvedQuizInfo();
            cpq.setCompletedAt(LocalDateTime.now());
            cpq.setUserName(userName);
            cpq.setId((long) quizId);
            SolvedQuizInfo save = solvedQuizInfoRepository.save(cpq);
            log.info("saved id {} for user {}", save.getId(), save.getUserName());
            return feedbackDao.getPositiveFeedback();
        }
        return feedbackDao.getNegativeFeedback();
    }

    public void deleteQuiz(int quizId, String userMail) {
        Quiz quiz = getQuizById(quizId);
        if (quiz.getAuthor().equals(userMail)) {
            quizDBRepository.deleteById(quizId);
        } else {
            throw new QuizNotSufficientPrivilegesException();
        }
    }

    private Quiz getQuizById(int quizId) {
        return quizDBRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));
    }
}
