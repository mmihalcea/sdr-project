import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserOrdersComponent} from './user-orders.component';
import {UserOrderDetailsComponent} from "./user-order-details/user-order-details.component";
import {AnyRoleGuard} from "../core/auth/guards/any-role.guard";
import {ProductDetailsResolver} from "../product/product-details/product-details-resolver.service";
import {UserOrderDetailsResolver} from "./user-order-details/user-order-details.resolver";

const routes: Routes = [{path: '', component: UserOrdersComponent},
  {
    path: ':orderId', component: UserOrderDetailsComponent,
    resolve: {details: UserOrderDetailsResolver}
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserOrdersRoutingModule {
}
