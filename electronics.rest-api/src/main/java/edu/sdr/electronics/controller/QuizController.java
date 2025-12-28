package edu.sdr.electronics.controller;


import edu.sdr.electronics.domain.QuizQuestion;
import edu.sdr.electronics.dto.request.AddQuestionRequest;
import edu.sdr.electronics.dto.response.QuizCompletionResponse;
import edu.sdr.electronics.dto.response.QuizQuestionResponse;
import edu.sdr.electronics.dto.response.QuizWrapperResponse;
import edu.sdr.electronics.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/quiz")
@AllArgsConstructor
public class QuizController {

    private QuizService quizService;

    @GetMapping()
    public ResponseEntity<QuizWrapperResponse> getAllQuestions(@RequestParam(required = false) boolean retry) {
        return new ResponseEntity<>(quizService.getAllQuestions(retry), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuizQuestionResponse>> addQuestion(@RequestBody AddQuestionRequest addQuestionRequest) {
        return new ResponseEntity<>(quizService.addQuestion(addQuestionRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuizQuestionResponse>> deleteQuestion(@PathVariable Long questionId) {
        return new ResponseEntity<>(quizService.deleteQuestion(questionId), HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuizQuestionResponse>> updateQuestion(@RequestBody QuizQuestion quizQuestion) {
        return new ResponseEntity<>(quizService.updateQuestion(quizQuestion), HttpStatus.OK);
    }

    @PostMapping("/complete")
    public ResponseEntity<QuizCompletionResponse> completeQuiz(@RequestBody Map<Long, Set<Long>> answers) {
        return new ResponseEntity<>(quizService.completeQuiz(answers), HttpStatus.OK);
    }
}
