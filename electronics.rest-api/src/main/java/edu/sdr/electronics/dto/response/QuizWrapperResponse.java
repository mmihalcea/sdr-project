package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuizWrapperResponse {

    private List<QuizQuestionResponse> questions;
    private QuizCompletionResponse quizCompletion;
}
