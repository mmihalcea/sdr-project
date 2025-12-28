import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Category} from "../../product/category.model";
import {QuizQuestion} from "../quiz-question-response.model";
import {AddQuestionRequest} from "./add-question-request.model";
import {AddAnswerRequest} from "./add-answer-request.model";
import {QuizService} from "../quiz.service";

@Component({
  selector: 'app-quiz-admin',
  templateUrl: './quiz-admin.component.html',
  styleUrls: ['./quiz-admin.component.scss']
})
export class QuizAdminComponent implements OnInit {
  categories: Category[] = [];
  questions: QuizQuestion[] = [];
  selectedCategory: number = -1;
  showAddDialog = false;
  newQuestion: AddQuestionRequest = new AddQuestionRequest();
  newAnswer = new AddAnswerRequest();


  constructor(private activatedRoute: ActivatedRoute, private quizService: QuizService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.categories = data.details[0];
      this.questions = data.details[1].questions;
    });
  }

  getCategoryForId(categoryId: number): Category {
    return this.categories.filter(i => i.id === categoryId)[0];
  }

  addNewAnswer() {
    this.newQuestion.answers = [...this.newQuestion.answers, this.newAnswer];
    this.newAnswer = new AddAnswerRequest();
  }

  deleteAnswer(answer: AddAnswerRequest) {
    const index = this.newQuestion.answers.indexOf(answer, 0);
    if (index > -1) {
      this.newQuestion.answers.splice(index, 1);
    }
  }

  resetFields() {
    this.newAnswer = new AddAnswerRequest();
    this.newQuestion = new AddQuestionRequest();
  }

  addNewQuestion() {
    this.quizService.addQuestion(this.newQuestion).subscribe(res => {
      this.resetFields();
      this.questions = res;
      this.showAddDialog = false
    })
  }

  deleteQuestion(id: number) {
    this.quizService.deleteQuestion(id).subscribe(res => {
      this.questions = res
    })
  }

  updateQuestion(quizQuestion: QuizQuestion) {
    this.quizService.updateQuestion(quizQuestion).subscribe(res => this.questions = res);
  }
}
