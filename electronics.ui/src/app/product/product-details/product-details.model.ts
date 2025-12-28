import {ProductItem} from "../instrument-list/product-item.model";
import {ProductReviewResponse} from "../instrument-reviews/instrument-review-response.model";

export interface ProductDetails extends ProductItem {
  description: string;
  datePosted: Date;
  rating: number;
  reviews: Array<ProductReviewResponse>
}
