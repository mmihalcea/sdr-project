import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {QuizService} from "./quiz.service";
import {QuizWrapper} from "./quiz-wrapper-response";

@Injectable({
  providedIn: 'root'
})
export class QuizResolver implements Resolve<QuizWrapper> {

  constructor(private quizService: QuizService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<QuizWrapper> {
    return this.quizService.getAllQuestions();
  }
}
