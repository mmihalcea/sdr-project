import {AddAnswerRequest} from "./add-answer-request.model";

export class AddQuestionRequest {
  text = '';
  answers: Array<AddAnswerRequest> = [];
}
