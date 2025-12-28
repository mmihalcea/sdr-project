import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ProductDetails} from "./product-details.model";
import {ProductService} from "../product.service";

@Injectable({
  providedIn: 'root'
})
export class ProductDetailsResolver implements Resolve<ProductDetails> {

  constructor(private productService: ProductService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProductDetails> {
      return this.productService.getProductDetails(route.params['productId']);
  }
}
