import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, Observable} from 'rxjs';
import {ProductService} from "../../product/product.service";
import {QuizService} from "../quiz.service";
import {Category} from "../../product/category.model";
import {QuizWrapper} from "../quiz-wrapper-response";

@Injectable({
  providedIn: 'root'
})
export class QuizAdminResolver implements Resolve<[Category[], QuizWrapper]> {

  constructor(private instrumentService: ProductService, private quizService: QuizService) {
  }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<[Category[], QuizWrapper]> {
    return forkJoin([this.instrumentService.getCategories(), this.quizService.getAllQuestions()]);
  }
}
