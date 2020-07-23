package com.home.quizws.quiz.model;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class QuizWithIdDto {

    private Integer id;
    private String title;

    private String text;
    private List<String> options;

    public QuizWithIdDto toQuiz(Quiz quiz) {
        return new QuizWithIdDtoBuilder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .text(quiz.getText())
                .options(convertOptions(quiz.getOptions()))
                .build();
    }

    private List<String> convertOptions(List<Option> options) {
        return options.stream()
                .map(Option::getOption)
                .collect(Collectors.toList());
    }
}
