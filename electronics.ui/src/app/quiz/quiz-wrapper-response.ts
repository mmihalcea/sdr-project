import {QuizQuestion} from "./quiz-question-response.model";
import {QuizCompletionResponse} from "./quiz-completion.response";

export class QuizWrapper {
  questions: Array<QuizQuestion> = [];
  quizCompletion: QuizCompletionResponse = new QuizCompletionResponse();
}
