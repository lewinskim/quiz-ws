package com.home.quizws.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @Size(min = 2, message = "Please provide minimum two options")
    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", nullable = false)
    private List<Option> options;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private List<QuizAnswer> answer;

    @NotBlank
    @JsonIgnore
    private String author;

}
