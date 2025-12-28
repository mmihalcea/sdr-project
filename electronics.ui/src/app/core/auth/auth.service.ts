import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {JwtResponse} from '../model/jwt-response';
import {AuthLoginInfo} from '../model/login-info';
import {environment} from "../../../environments/environment";
import {ResponseMessage} from "../model/response-message";
import {StoreUser} from "../model/store-user";
import {County} from "../../register/county-response.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly loginUrl = environment.apiUrl + '/auth/login';

  private readonly forgotPasswordUrl = environment.apiUrl + '/auth/forgot-password';


  constructor(private http: HttpClient) {
  }

  login(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials);
  }

  forgotPassword(email: string): Observable<ResponseMessage> {
    return this.http.get<ResponseMessage>(this.forgotPasswordUrl + '?email=' + email);
  }



}
