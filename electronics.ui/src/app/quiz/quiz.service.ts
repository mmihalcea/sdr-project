import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {QuizQuestion} from "./quiz-question-response.model";
import {AddQuestionRequest} from "./quiz-admin/add-question-request.model";
import {QuizCompletionResponse} from "./quiz-completion.response";
import {QuizWrapper} from "./quiz-wrapper-response";

@Injectable({
  providedIn: 'root'
})
export class QuizService {
  private readonly quizUrl = environment.apiUrl + '/quiz';
  private readonly quizCompletionUrl = this.quizUrl + '/complete';

  constructor(private http: HttpClient) {
  }

  getAllQuestions(retry?: boolean): Observable<QuizWrapper> {
    return this.http.get<QuizWrapper>(this.quizUrl + (retry !== undefined ? '?retry=' + retry : ''));
  }

  addQuestion(request: AddQuestionRequest): Observable<Array<QuizQuestion>> {
    return this.http.post<Array<QuizQuestion>>(this.quizUrl, request);
  }

  deleteQuestion(questionId: number): Observable<Array<QuizQuestion>> {
    return this.http.delete<Array<QuizQuestion>>(this.quizUrl + `/${questionId}`);
  }

  updateQuestion(quizQuestion: QuizQuestion): Observable<Array<QuizQuestion>> {
    return this.http.put<Array<QuizQuestion>>(this.quizUrl, quizQuestion);
  }

  completeQuiz(quizAnswers: Map<number, Array<number>>): Observable<QuizCompletionResponse> {
    let convMap: { [key: number]: number[] | undefined } = {};
    quizAnswers.forEach((val: number[], key: number) => {
      convMap[key] = val;
    });
    return this.http.post<QuizCompletionResponse>(this.quizCompletionUrl, convMap);
  }
}
