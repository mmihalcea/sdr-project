import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderMapComponent } from './order-map.component';

const routes: Routes = [{ path: '', component: OrderMapComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderMapRoutingModule { }
