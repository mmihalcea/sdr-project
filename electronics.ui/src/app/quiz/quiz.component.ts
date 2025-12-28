import {Component, OnInit} from '@angular/core';
import {QuizQuestion} from "./quiz-question-response.model";
import {MenuItem} from "primeng/api";
import {ActivatedRoute} from "@angular/router";
import {QuizService} from "./quiz.service";
import {QuizCompletionResponse} from "./quiz-completion.response";

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {
  questions: QuizQuestion[] = [];
  menuItems: MenuItem[] = [];
  quizCompletion: QuizCompletionResponse = new QuizCompletionResponse();
  quizQuestionAnswers = new Map<number, Array<number>>();
  selectedAnswers = new Array<number>();
  activeIndex = 0;
  showQuizResults = false;

  constructor(private activatedRoute: ActivatedRoute, private quizService: QuizService) {
    this.activatedRoute.data.subscribe((data) => {
      if (data.questions.quizCompletion) {
        this.showQuizResults = true;
        this.quizCompletion = data.questions.quizCompletion;
      } else {
        this.questions = data.questions.questions;
      }

    });
  }

  ngOnInit(): void {
    this.getMenuItems();
  }

  nextQuestion() {
    this.quizQuestionAnswers.set(this.questions[this.activeIndex].id, this.selectedAnswers);
    this.selectedAnswers = [];
    if (this.activeIndex === this.questions.length - 1) {
      this.quizService.completeQuiz(this.quizQuestionAnswers).subscribe(res => {
        this.quizCompletion = res;
        this.showQuizResults = true;
      })
    } else {
      this.activeIndex++;
    }
  }

  async retryQuiz() {
    this.quizService.getAllQuestions(true).subscribe(res => {
      this.questions = res.questions;
      this.showQuizResults = false;
      this.quizCompletion = new QuizCompletionResponse();
      this.getMenuItems();
    })
  }

  private getMenuItems() {
    this.activeIndex = 0;
    this.menuItems = [];
    for (let i = 1; i <= this.questions.length; i++) {
      this.menuItems.push({label: `Întrebarea ${i}`});
    }
  }
}
