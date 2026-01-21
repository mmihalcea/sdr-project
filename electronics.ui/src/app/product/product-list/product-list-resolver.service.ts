import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ProductItem} from "./product-item.model";
import {ProductService} from "../product.service";

@Injectable({
  providedIn: 'root'
})
export class ProductListResolver implements Resolve<Array<ProductItem>> {


  constructor(private productService: ProductService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ProductItem>> {
    return this.productService.getAllProductsWithRecommendations();
  }
}
