import {OrderLineResponse} from "./order-line-response.model";
import {OrderResponse} from "../order-response.model";

export class OrderDetailsResponse extends OrderResponse {
  orderLines: Array<OrderLineResponse> = [];
}
