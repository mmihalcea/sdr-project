import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {MessageService} from 'primeng/api';
import {environment} from "../../environments/environment";
import {OrderRequest} from "./order-request.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  products = new Map<number, ShoppingCartInstrumentItem>();
  private _orderLinesUpdateSource = new Subject<Map<number, ShoppingCartInstrumentItem>>();
  orderLinesUpdate$ = this._orderLinesUpdateSource.asObservable();

  private readonly saveOrderUrl = environment.apiUrl + '/order/save';

  constructor(private messageService: MessageService, private http: HttpClient) {
  }

  addInstrument(id: number, quantity: number) {
    if (this.products.has(id)) {
      let instrumentItem = this.products.get(id);
      if (instrumentItem !== undefined) {
        this.products.set(id, {quantity: instrumentItem.quantity + quantity});
        this._orderLinesUpdateSource.next(this.products);
        this.messageService.add({severity: 'success', detail: 'Informații actualizate!'});
      }
    } else {
      this.products.set(id, {quantity});
      this._orderLinesUpdateSource.next(this.products);
      this.messageService.add({severity: 'success', detail: 'Produs adaugat in cos'})
    }
  }


  removeInstrument(instrumentId: number) {
    this.products.delete(instrumentId);
    this._orderLinesUpdateSource.next(this.products);
  }

  saveOrder(orderRequest: OrderRequest): Observable<any> {
    return this.http.post(this.saveOrderUrl, orderRequest);
  }

  clear() {
    this.products = new Map<number, ShoppingCartInstrumentItem>();
    this._orderLinesUpdateSource.next(this.products);
  }
}

export interface ShoppingCartInstrumentItem {
  quantity: number;
}
