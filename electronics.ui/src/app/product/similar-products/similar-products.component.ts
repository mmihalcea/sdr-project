import {Component, Input, OnInit} from '@angular/core';
import {ProductItem} from "../product-list/product-item.model";
import {getSeverityByStockStatusId} from "../../utils/functions";

@Component({
  selector: 'app-similar-products',
  templateUrl: './similar-products.component.html',
  styleUrls: ['./similar-products.component.scss']
})
export class SimilarProductsComponent implements OnInit {

  @Input() similarProducts: ProductItem[] = [];
  @Input() title: string = "";

  constructor() { }

  ngOnInit(): void {
  }

  openProduct(id: number) {
    window.location.assign(`/product/${id}`);
  }

  protected readonly getSeverityByStockStatusId = getSeverityByStockStatusId;
}
