import {ProductItem} from "../../product/instrument-list/product-item.model";

export class OrderLineResponse {
  quantity = 0;
  price = 0;
  product: ProductItem | null = null;
}
