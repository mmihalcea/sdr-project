import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddProductComponent} from './add-product/add-product.component';
import {ProductDetailsComponent} from './product-details/product-details.component';
import {AddProductResolver} from './add-product/add-product-resolver.service';
import {productListComponent} from './product-list/product-list.component';
import {ProductListResolver} from './product-list/product-list-resolver.service';
import {ProductDetailsResolver} from './product-details/product-details-resolver.service';
import {AdminGuard} from '../core/auth/guards/admin.guard';
import {AnyRoleGuard} from '../core/auth/guards/any-role.guard';

const routes: Routes = [{
  path: '',
  component: productListComponent,
  resolve: {products: ProductListResolver}
}, {
  path: 'add',
  component: AddProductComponent,
  canActivate: [AdminGuard],
  resolve: {categories: AddProductResolver}
},
  {
    path: ':productId',
    component: ProductDetailsComponent,
    canActivate: [AnyRoleGuard],
    resolve: {details: ProductDetailsResolver}
  },
  {
    path: 'update/:productId',
    loadChildren: () => import('./update-product/update-product.module').then(m => m.UpdateProductModule),
    canActivate: [AdminGuard]
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule {
}
