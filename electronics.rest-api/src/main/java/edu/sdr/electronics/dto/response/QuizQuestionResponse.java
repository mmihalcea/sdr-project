package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizQuestionResponse {
    private Long id;
    private String text;
    private List<QuizAnswerResponse> answers;
}
