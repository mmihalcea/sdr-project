import {ProductReviewRequest} from "./instrument-review-request.model";

export class ProductReviewResponse extends ProductReviewRequest {
  datePosted = new Date();
  user: { username: string } = {username: ''};
}
