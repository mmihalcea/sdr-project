import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserOrdersRoutingModule } from './user-orders-routing.module';
import { UserOrdersComponent } from './user-orders.component';
import {SharedModule} from "../shared/shared.module";
import { UserOrderDetailsComponent } from './user-order-details/user-order-details.component';
import {ListboxModule} from "primeng/listbox";


@NgModule({
  declarations: [
    UserOrdersComponent,
    UserOrderDetailsComponent
  ],
  imports: [
    SharedModule,
    UserOrdersRoutingModule,
    ListboxModule
  ]
})
export class UserOrdersModule { }
