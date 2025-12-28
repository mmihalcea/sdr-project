import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {forkJoin, Observable} from 'rxjs';
import {ProfileUser} from "../core/model/profile-user";
import {UserProfileService} from "./user-profile.service";
import {TokenStorageService} from "../core/auth/token-storage.service";
import {Category} from "../product/category.model";
import {ProductService} from "../product/product.service";

@Injectable({
  providedIn: 'root'
})
export class UserProfileResolver implements Resolve<[ProfileUser, Category[]]> {

  constructor(private userProfileService: UserProfileService, private tokenStorageService: TokenStorageService, private instrumentService: ProductService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<[ProfileUser, Category[]]> {
    return forkJoin([this.userProfileService.getUserDetails(this.tokenStorageService.getUsername()), this.instrumentService.getCategories()]);
  }
}
