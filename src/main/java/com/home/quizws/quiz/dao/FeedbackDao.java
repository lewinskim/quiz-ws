package com.home.quizws.quiz.dao;

import com.home.quizws.quiz.model.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackDao {

    public Feedback getPositiveFeedback() {
        return new Feedback(true, "Congratulations, you're right!");
    }

    public Feedback getNegativeFeedback() {
        return new Feedback(false, "Wrong answer! Please, try again.");
    }

}
