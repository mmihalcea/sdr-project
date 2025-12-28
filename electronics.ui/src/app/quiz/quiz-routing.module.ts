import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {QuizComponent} from './quiz.component';
import {UserGuard} from "../core/auth/guards/user.guard";
import {QuizAdminComponent} from "./quiz-admin/quiz-admin.component";
import {AdminGuard} from "../core/auth/guards/admin.guard";
import {QuizResolver} from "./quiz.resolver";
import {QuizAdminResolver} from "./quiz-admin/quiz-admin.resolver";

const routes: Routes = [{path: '', component: QuizComponent, canActivate: [UserGuard], resolve: {questions: QuizResolver}},
  {path: 'manage', component: QuizAdminComponent, canActivate: [AdminGuard], resolve: {details: QuizAdminResolver}}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuizRoutingModule {
}
