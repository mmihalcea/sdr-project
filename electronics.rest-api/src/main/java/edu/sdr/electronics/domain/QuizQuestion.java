package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuizAnswer> answers = new ArrayList<>();
}