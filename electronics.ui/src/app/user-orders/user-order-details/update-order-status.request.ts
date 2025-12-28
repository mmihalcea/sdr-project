export class UpdateOrderStatus {
  orderId = -1;
  orderStatus = -1;


  constructor(orderId: number, orderStatus: number) {
    this.orderId = orderId;
    this.orderStatus = orderStatus;
  }
}
