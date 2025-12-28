import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ChartResponse} from "./chart-response.model";
import {ChartService} from "./chart.service";

@Injectable({
  providedIn: 'root'
})
export class ChartResolver implements Resolve<ChartResponse> {

  constructor(private chartService: ChartService) {
  }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChartResponse> {
    return this.chartService.getChartData();
  }
}
