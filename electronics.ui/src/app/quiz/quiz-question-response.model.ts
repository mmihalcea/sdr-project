import {QuizAnswer} from "./quiz-answer-response.model";

export class QuizQuestion {
  id = -1;
  text = '';
  answers: Array<QuizAnswer> = [];
}
