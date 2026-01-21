import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ProductItem} from '../product/product-list/product-item.model';
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
  products: Array<ProductItem> = [];
  alsoBought: Array<ProductItem> = [];
  alsoBoughtTitle: string = "";
  getSeverityByStockStatusId = getSeverityByStockStatusId;
  priceTotal = 0;
  quantityDaysRentedMap = new Map<number, ShoppingCartInstrumentItem>();

  constructor(private activatedRoute: ActivatedRoute, public shoppingCartService: ShoppingCartService,
              private instrumentService: ProductService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.products = data.products.shoppingCartProducts;
      this.alsoBought = data.products.alsoBought;
      this.alsoBoughtTitle = "Clientii care au cumparat " + data.products.alsoBoughtTitle + " au cumparat si...";
      this.quantityDaysRentedMap = this.shoppingCartService.products;
      if (this.products !== null && this.products.length > 0) {
        this.priceTotal = this.products.map(i => i.price * this.quantityDaysRentedMap.get(i.id)!.quantity)?.reduce((a, b) => a + b);
      }
    });

    this.shoppingCartService.orderLinesUpdate$.subscribe(res => {
      this.products = this.products.filter(i => res.has(i.id));
      this.quantityDaysRentedMap = res;
      if (this.products.length > 0) {
        this.priceTotal = this.products?.map(i => i.price * this.quantityDaysRentedMap.get(i.id)!.quantity)?.reduce((a, b) => a + b);
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
      orderLines.push({productId: k, quantity: v.quantity});
    })
    orderRequest.orderLines = orderLines;

    this.shoppingCartService.saveOrder(orderRequest).subscribe(() => {
      this.shoppingCartService.clear();
      this.messageService.add({severity: 'success', summary: "Comandă trimisă!"});
    });

  }
}
