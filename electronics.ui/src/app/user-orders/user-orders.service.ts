import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {OrderResponse} from './order-response.model';
import {OrderDetailsResponse} from "./user-order-details/order-details-response.model";
import {IdValue} from "../utils/id-value.model";
import {UpdateOrderStatus} from "./user-order-details/update-order-status.request";

@Injectable({
  providedIn: 'root'
})
export class UserOrdersService {
  private readonly userOrdersUrl = environment.apiUrl + '/order/user';
  private readonly userOrderUrl = environment.apiUrl + '/order';
  private readonly userOrderStatusesUrl = environment.apiUrl + '/order/statuses';

  constructor(private http: HttpClient) {
  }

  getUserOrders(): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(this.userOrdersUrl);
  }

  getAllOrders(): Observable<OrderResponse> {
    return this.http.get<OrderResponse>(this.userOrderUrl);
  }

  getStatuses(): Observable<Array<IdValue>> {
    return this.http.get<Array<IdValue>>(this.userOrderStatusesUrl);
  }

  getOrderDetails(orderId: number): Observable<OrderDetailsResponse> {
    return this.http.get<OrderDetailsResponse>(this.userOrderUrl + '/' + orderId);
  }

  updateOrderStatus(request: UpdateOrderStatus): Observable<any> {
    return this.http.put<OrderDetailsResponse>(this.userOrderUrl,request);
  }


}
