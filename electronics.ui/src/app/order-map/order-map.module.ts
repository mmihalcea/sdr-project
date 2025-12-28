import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OrderMapRoutingModule } from './order-map-routing.module';
import { OrderMapComponent } from './order-map.component';
import {SharedModule} from '../shared/shared.module';


@NgModule({
  declarations: [
    OrderMapComponent
  ],
  imports: [
    SharedModule,
    OrderMapRoutingModule
  ]
})
export class OrderMapModule { }
