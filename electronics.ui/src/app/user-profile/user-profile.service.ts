import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProfileUser} from "../core/model/profile-user";
import {environment} from "../../environments/environment";
import {ResponseMessage} from "../core/model/response-message";

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {
  private readonly userDetailsUrl = environment.apiUrl + '/user/details';
  private readonly updateUserUrl = environment.apiUrl + '/user/update';
  private readonly updateCategoriesUrl = environment.apiUrl + '/user/update-product-types';

  constructor(private http: HttpClient) {
  }

  getUserDetails(username: string | null): Observable<ProfileUser> {
    return this.http.get<ProfileUser>(this.userDetailsUrl + '?username=' + username);
  }

  updateUser(user: ProfileUser): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.updateUserUrl, user);
  }

  updateCategories(categories: number[]): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.updateCategoriesUrl, categories);
  }
}
