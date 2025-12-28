import {IdValue} from "../utils/id-value.model";

export class OrderResponse {
  id = -1;
  priceTotal = 0;
  orderDate: Date = new Date();
  orderStatus: IdValue = new IdValue();

}
