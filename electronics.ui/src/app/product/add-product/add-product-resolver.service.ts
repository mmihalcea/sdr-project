import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ProductService} from "../product.service";
import {Category} from "../category.model";

@Injectable({
  providedIn: 'root'
})
export class AddProductResolver implements Resolve<Category[]> {

  constructor(private instrumentService: ProductService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Category[]> {
    return this.instrumentService.getCategories();
  }
}
