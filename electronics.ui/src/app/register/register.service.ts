import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {County} from "./county-response.model";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {StoreUser} from "../core/model/store-user";
import {ResponseMessage} from "../core/model/response-message";
import {FeatureCollection} from "geojson";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  private readonly countiesUrl = environment.apiUrl + '/auth/counties';
  private readonly registerUrl = environment.apiUrl + '/auth/register';
  private readonly photonQueryUrl = environment.photonApiUrl + '?q=';

  constructor(private http: HttpClient) {
  }

  getCounties(): Observable<Array<County>> {
    return this.http.get<Array<County>>(this.countiesUrl);
  }

  register(info: StoreUser): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.registerUrl, info);
  }

  searchStreet(query: String): Observable<FeatureCollection> {
    return this.http.get<FeatureCollection>(this.photonQueryUrl + query + '&limit=50');
  }

}
