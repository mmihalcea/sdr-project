import {OrderLineRequest} from "./order-line-request.model";

export class OrderRequest {
  orderLines: Array<OrderLineRequest> = [];
}
