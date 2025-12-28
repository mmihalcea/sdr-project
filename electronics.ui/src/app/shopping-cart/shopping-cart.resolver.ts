import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable, of, throwError} from 'rxjs';
import {ProductItem} from '../product/instrument-list/product-item.model';
import {ProductService} from '../product/product.service';
import {ShoppingCartService} from './shopping-cart.service';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartResolver implements Resolve<Array<ProductItem>> {

  constructor(private instrumentService: ProductService, private shoppingCartService: ShoppingCartService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ProductItem>> {
    if (this.shoppingCartService.products.size > 0) {
      return this.instrumentService.getAllProducts([...this.shoppingCartService.products.keys()]);
    } else {
      return of([]);
    }

  }
}
