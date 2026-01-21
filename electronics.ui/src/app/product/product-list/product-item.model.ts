import {IdName} from "../../utils/id-name.model";
import {IdValue} from "../../utils/id-value.model";

export interface ProductItem {
  id: number;
  name: string;
  price: number;
  category: IdName;
  photos: Array<string>;
  stockStatus: IdValue;
  averageRating: number;
}
