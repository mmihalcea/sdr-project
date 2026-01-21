import {ProductItem} from "../product-list/product-item.model";

export interface ProductCombinedListResponse{
  products: ProductItem[];
  recommendations: ProductItem[];
}
