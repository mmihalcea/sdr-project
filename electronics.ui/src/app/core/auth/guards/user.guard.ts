import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../token-storage.service';
import {Role} from '../../../utils/role';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {
  constructor(private tokenStorageService: TokenStorageService) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.tokenStorageService.getAuthorities().includes(Role.USER);
  }

}
