import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {AdminOrderResponse} from "../user-orders/admin-order-response.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderMapService {
  private readonly ordersUrl = environment.apiUrl + '/order';

  constructor(private http: HttpClient) {
  }

  getAllActiveOrders(): Observable<AdminOrderResponse[]> {
    return this.http.get<AdminOrderResponse[]>(this.ordersUrl);
  }
}
