import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of, throwError} from 'rxjs';
import {ProductItem} from '../product/product-list/product-item.model';
import {ProductService} from '../product/product.service';
import {ShoppingCartService} from './shopping-cart.service';
import {ShoppingCartResponse} from "./shopping-cart-response";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartResolver implements Resolve<ShoppingCartResponse> {

  constructor(private instrumentService: ProductService, private shoppingCartService: ShoppingCartService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ShoppingCartResponse> {
    if (this.shoppingCartService.products.size > 0) {
      return this.instrumentService.getAllProductsFromShoppingCart([...this.shoppingCartService.products.keys()]);
    } else {
      return of();
    }

  }
}
