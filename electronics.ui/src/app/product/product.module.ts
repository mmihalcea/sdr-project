import {NgModule} from '@angular/core';

import {ProductRoutingModule} from './product-routing.module';
import {AddProductComponent} from './add-product/add-product.component';
import {ProductDetailsComponent} from './product-details/product-details.component';
import {SharedModule} from '../shared/shared.module';
import {FileUploadModule} from 'primeng/fileupload';
import {ProductListComponent} from './product-list/product-list.component';
import {InstrumentReviewsComponent} from './product-reviews/instrument-reviews.component';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {AddToCartDialogComponent} from './add-to-cart-dialog/add-to-cart-dialog.component';
import { SimilarProductsComponent } from './similar-products/similar-products.component';
import { ProductCombinedListComponent } from './product-combined-list/product-combined-list.component';


@NgModule({
  declarations: [
    AddProductComponent,
    ProductDetailsComponent,
    ProductListComponent,
    InstrumentReviewsComponent,
    AddToCartDialogComponent,
    SimilarProductsComponent,
    ProductCombinedListComponent
  ],
  exports: [
    ProductListComponent,
    SimilarProductsComponent
  ],
  imports: [
    SharedModule,
    ProductRoutingModule,
    FileUploadModule,
    InputTextareaModule
  ]
})
export class ProductModule {
}
