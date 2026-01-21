import {ProductItem} from "../../product/product-list/product-item.model";

export class OrderLineResponse {
  quantity = 0;
  price = 0;
  product: ProductItem | null = null;
}
