package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    @ManyToOne
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "question_id" , nullable = false)
    private QuizQuestion question;


    public QuizAnswer(Long id) {
        this.id = id;
    }

    public QuizAnswer(String text, Category category, QuizQuestion question) {
        this.text = text;
        this.category = category;
        this.question = question;
    }
}
