import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "../core/auth/auth.service";
import {County} from "./county-response.model";
import {RegisterService} from "./register.service";

@Injectable({
  providedIn: 'root'
})
export class RegisterResolver implements Resolve<Array<County>> {
  constructor(private registerService: RegisterService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<County>> {
    return this.registerService.getCounties();
  }
}
