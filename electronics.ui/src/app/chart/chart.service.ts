import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ChartResponse} from "./chart-response.model";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ChartService {
  private readonly chartUrl = environment.apiUrl + '/chart';

  constructor(private http: HttpClient) {
  }


  getChartData(): Observable<ChartResponse> {
    return this.http.get<ChartResponse>(this.chartUrl);
  }
}
