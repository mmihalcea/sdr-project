import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProductItem} from '../product/instrument-list/product-item.model';
import {getSeverityByStockStatusId} from '../utils/functions';
import {ShoppingCartInstrumentItem, ShoppingCartService} from './shopping-cart.service';
import {ProductService} from '../product/product.service';
import {OrderRequest} from "./order-request.model";
import {OrderLineRequest} from "./order-line-request.model";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit {
  instruments: Array<ProductItem> = [];
  getSeverityByStockStatusId = getSeverityByStockStatusId;
  priceTotal = 0;
  quantityDaysRentedMap = new Map<number, ShoppingCartInstrumentItem>();

  constructor(private activatedRoute: ActivatedRoute, public shoppingCartService: ShoppingCartService,
              private instrumentService: ProductService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.instruments = data.instruments;
      this.quantityDaysRentedMap = this.shoppingCartService.products;
      if (this.instruments !== null && this.instruments.length > 0) {
        this.priceTotal = this.instruments.map(i => i.price * this.quantityDaysRentedMap.get(i.id)!.quantity)?.reduce((a, b) => a + b);
      }
    });

    this.shoppingCartService.orderLinesUpdate$.subscribe(res => {
      this.instruments = this.instruments.filter(i => res.has(i.id));
      this.quantityDaysRentedMap = res;
      if (this.instruments.length > 0) {
        this.priceTotal = this.instruments?.map(i => i.price * this.quantityDaysRentedMap.get(i.id)!.quantity)?.reduce((a, b) => a + b);
      } else {
        this.priceTotal = 0;
      }
    })
  }

  deleteInstrumentFromCart(id: number) {
    this.shoppingCartService.removeInstrument(id);
  }


  saveOrder() {
    const orderRequest = new OrderRequest();
    const orderLines = new Array<OrderLineRequest>();

    this.quantityDaysRentedMap.forEach((v, k) => {
      orderLines.push({instrumentId: k, quantity: v.quantity});
    })
    orderRequest.orderLines = orderLines;

    this.shoppingCartService.saveOrder(orderRequest).subscribe(() => {
      this.shoppingCartService.clear();
      this.messageService.add({severity: 'success', summary: "Comandă trimisă!"});
    });

  }
}
