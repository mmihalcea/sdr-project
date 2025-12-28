import {OrderResponse} from "./order-response.model";
import {OrderUserResponse} from "./order-user.response";

export class AdminOrderResponse extends OrderResponse {
  user = new OrderUserResponse();
}
