import {ProductItem} from "../product-list/product-item.model";
import {ProductReviewResponse} from "../product-reviews/instrument-review-response.model";

export interface ProductDetails extends ProductItem {
  description: string;
  datePosted: Date;
  rating: number;
  reviews: Array<ProductReviewResponse>
  similarProducts: Array<ProductItem>
  alsoBoughtProducts: Array<ProductItem>
}
