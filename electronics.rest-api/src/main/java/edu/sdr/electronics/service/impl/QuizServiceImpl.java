package edu.sdr.electronics.service.impl;

import edu.sdr.electronics.domain.Category;
import edu.sdr.electronics.domain.QuizAnswer;
import edu.sdr.electronics.domain.QuizQuestion;
import edu.sdr.electronics.domain.StoreUser;
import edu.sdr.electronics.dto.request.AddQuestionRequest;
import edu.sdr.electronics.dto.response.*;
import edu.sdr.electronics.repository.*;
import edu.sdr.electronics.service.QuizService;
import edu.sdr.electronics.dto.response.QuizCompletionResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final CategoryRepository categoryRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ProductReviewRepository productReviewRepository;
    private final StoreUserRepository storeUserRepository;

    @Override
    public QuizWrapperResponse getAllQuestions(boolean retry) {
        QuizWrapperResponse response = new QuizWrapperResponse();
        modelMapper.typeMap(QuizAnswer.class, QuizAnswerResponse.class).addMapping(src -> src.getCategory().getId(), QuizAnswerResponse::setCategory);

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        final StoreUser storeUser = storeUserRepository.findByUsername(principal.getName()).orElse(null);
        if (storeUser != null && storeUser.getAnswers() != null && !storeUser.getAnswers().isEmpty()) {
            if (retry) {
                storeUser.setAnswers(new HashSet<>());
                storeUserRepository.save(storeUser);
            } else {
                Map<Long, Set<Long>> answers = storeUser.getAnswers().stream().collect(Collectors.groupingBy(s -> s.getQuestion().getId(), Collectors.mapping(QuizAnswer::getId, Collectors.toSet())));
                response.setQuizCompletion(completeQuiz(answers));
                return response;
            }
        }

        List<QuizQuestionResponse> questions = modelMapper.map(quizQuestionRepository.findAll(), new TypeToken<List<QuizQuestionResponse>>() {
        }.getType());
        response.setQuestions(questions);
        return response;
    }

    @Override
    public List<QuizQuestionResponse> addQuestion(AddQuestionRequest addQuestionRequest) {
        QuizQuestion quizQuestion = mapQuizQuestion(addQuestionRequest);
        quizQuestionRepository.save(quizQuestion);

        return getAllQuestions(false).getQuestions();
    }

    private QuizQuestion mapQuizQuestion(AddQuestionRequest addQuestionRequest) {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setText(addQuestionRequest.getText());
        quizQuestion.setAnswers(addQuestionRequest.getAnswers().stream().map(a -> new QuizAnswer(a.getText(), new Category(a.getCategory()), quizQuestion)).collect(Collectors.toList()));
        return quizQuestion;
    }

    @Override
    public List<QuizQuestionResponse> deleteQuestion(Long questionId) {
        quizQuestionRepository.findById(questionId).ifPresent(quizQuestionRepository::delete);
        return getAllQuestions(false).getQuestions();
    }

    @Override
    public List<QuizQuestionResponse> updateQuestion(QuizQuestion quizQuestion) {
        quizQuestionRepository.findById(quizQuestion.getId()).ifPresent(question -> {
            quizQuestion.getAnswers().forEach(a -> a.setQuestion(question));
            quizQuestionRepository.save(quizQuestion);
        });
        return getAllQuestions(false).getQuestions();
    }

    @Override
    public QuizCompletionResponse completeQuiz(Map<Long, Set<Long>> answers) {
        QuizCompletionResponse response = new QuizCompletionResponse();
        Map<Long, Long> categories = answers.entrySet()
                .stream()
                .flatMap(e -> e.getValue().stream().map(value -> this.quizAnswerRepository.findById(value).orElse(null)))
                .map(i -> i.getCategory().getId())
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        Long maxOcc = categories.values().stream().reduce(BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(categories.values(), o)))).orElse(null);
        List<Long> frequentCategories = categories.keySet().stream().filter(t -> categories.get(t).equals(maxOcc)).collect(Collectors.toList());

        response.setCategories(frequentCategories.stream().map(i -> this.categoryRepository.findById(i).get().getName()).collect(Collectors.toList()));

        frequentCategories.forEach(type -> {
            response.getProducts().addAll(this.productRepository.findTop3ByCategoryIdOrderByPriceAsc(type).stream().map(i -> {
                ProductItem res = modelMapper.map(i, ProductItem.class);
                res.setAverageRating(productReviewRepository.getAverageRatingByProductId(i.getId()));
                //res.setPhotos(Collections.singletonList(Base64.getEncoder().encodeToString(i.getPhotos().get(0).getPhoto())));
                return res;
            }).collect(Collectors.toList()));
        });

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        final StoreUser storeUser = storeUserRepository.findByUsername(principal.getName()).orElse(null);
        if (storeUser != null) {
            answers.values().forEach(ans -> {
                storeUser.getAnswers().addAll(ans.stream().map(QuizAnswer::new).collect(Collectors.toSet()));
            });


            storeUserRepository.save(storeUser);

        }


        return response;
    }
}
