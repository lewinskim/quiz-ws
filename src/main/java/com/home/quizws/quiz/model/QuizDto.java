package com.home.quizws.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class QuizDto {

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @Size(min = 2, message = "Please provide minimum two options")
    @NotNull
    private List<String> options;

    private List<Integer> answer;

    public Quiz toQuiz(String userMail) {
        List<Option> convertedOptions = options.stream()
                .map(this::convertToOption)
                .collect(Collectors.toList());

        List<QuizAnswer> convertedAnswers = Collections.emptyList();
        System.out.println("answers are : " + answer);

        if (!answer.isEmpty()) {
            convertedAnswers = answer.stream()
                    .map(this::convertToAnswer)
                    .collect(Collectors.toList());
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setText(text);
        quiz.setAnswer(convertedAnswers);
        quiz.setOptions(convertedOptions);
        quiz.setAuthor(userMail);
        return quiz;
    }

    private QuizAnswer convertToAnswer(Integer integer) {
        QuizAnswer answer = new QuizAnswer();
        answer.setAnswer(integer);
        return answer;
    }

    private Option convertToOption(String s) {
        Option option = new Option();
        option.setOption(s);
        return option;
    }

}
