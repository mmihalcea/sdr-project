import {Component, OnInit} from '@angular/core';
import {SelectItem} from "primeng/api";
import {ShoppingCartService} from "../../shopping-cart/shopping-cart.service";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-add-to-cart-dialog',
  templateUrl: './add-to-cart-dialog.component.html',
  styleUrls: ['./add-to-cart-dialog.component.scss']
})
export class AddToCartDialogComponent implements OnInit {

  quantitiesOptions: SelectItem[] = [];
  selectedQuantity: number = 1;


  constructor(private shoppingCartService: ShoppingCartService, public config: DynamicDialogConfig, public ref: DynamicDialogRef) {
  }

  ngOnInit(): void {
    this.quantitiesOptions = [{label: '1', value: 1}, {label: '2', value: 2}, {label: '3', value: 3}, {
      label: '4', value: 4
    }, {label: '5', value: 5}]
  }

  addToShoppingCart() {
    this.shoppingCartService.addInstrument(this.config.data.id, this.selectedQuantity);
    this.selectedQuantity =1;

    this.ref.close();
  }
}
