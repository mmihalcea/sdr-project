import { Component, OnInit } from '@angular/core';
import {ProductItem} from "../product-list/product-item.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-product-combined-list',
  templateUrl: './product-combined-list.component.html',
  styleUrls: ['./product-combined-list.component.scss']
})
export class ProductCombinedListComponent implements OnInit {
  products: ProductItem[] = [];
  recommendations: ProductItem[] = [];

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.products.length === 0) {
      this.activatedRoute.data.subscribe((data) => {
        this.products = data.products.products;
        this.recommendations = data.products.recommendations;
      });
    }
  }

}
