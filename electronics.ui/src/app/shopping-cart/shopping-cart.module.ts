import {NgModule} from '@angular/core';

import {ShoppingCartRoutingModule} from './shopping-cart-routing.module';
import {ShoppingCartComponent} from './shopping-cart.component';
import {SharedModule} from "../shared/shared.module";
import {OrderListModule} from "primeng/orderlist";
import {ListboxModule} from 'primeng/listbox';


@NgModule({
  declarations: [
    ShoppingCartComponent
  ],
  imports: [
    SharedModule,
    ShoppingCartRoutingModule,
    OrderListModule,
    ListboxModule
  ]
})
export class ShoppingCartModule {
}
