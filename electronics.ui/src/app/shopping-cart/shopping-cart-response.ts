import {ProductItem} from "../product/product-list/product-item.model";

export interface ShoppingCartResponse {
  shoppingCartProducts: ProductItem[];
  alsoBought: ProductItem[];
  alsoBoughtTitle: string
}
