package edu.sdr.electronics.service;

import edu.sdr.electronics.domain.QuizQuestion;
import edu.sdr.electronics.dto.request.AddQuestionRequest;
import edu.sdr.electronics.dto.response.QuizCompletionResponse;
import edu.sdr.electronics.dto.response.QuizQuestionResponse;
import edu.sdr.electronics.dto.response.QuizWrapperResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QuizService {

    QuizWrapperResponse getAllQuestions(boolean retry);

    List<QuizQuestionResponse> addQuestion(AddQuestionRequest addQuestionRequest);

    List<QuizQuestionResponse> deleteQuestion(Long questionId);

    List<QuizQuestionResponse> updateQuestion(QuizQuestion quizQuestion);

    QuizCompletionResponse completeQuiz(Map<Long, Set<Long>> answers);


}
