import {StoreUser} from "../core/model/store-user";
import {ProductItem} from "../product/instrument-list/product-item.model";

export class QuizCompletionResponse{
  categories = new Array<number>();
  products = new Array<ProductItem>();
}
