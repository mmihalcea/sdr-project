import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, Observable} from 'rxjs';
import {UserOrdersService} from "../user-orders.service";
import {OrderDetailsResponse} from "./order-details-response.model";
import {IdValue} from "../../utils/id-value.model";

@Injectable({
  providedIn: 'root'
})
export class UserOrderDetailsResolver implements Resolve<[IdValue[], OrderDetailsResponse]> {

  constructor(private ordersService: UserOrdersService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<[IdValue[], OrderDetailsResponse]> {

    return forkJoin([this.ordersService.getStatuses(), this.ordersService.getOrderDetails(route.params['orderId'])]);

  }
}
