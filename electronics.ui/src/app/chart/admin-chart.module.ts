import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ChartRoutingModule} from './chart-routing.module';
import {ChartComponent} from './chart.component';
import {ChartModule} from "primeng/chart";
import {SharedModule} from "../shared/shared.module";


@NgModule({
  declarations: [
    ChartComponent
  ],
    imports: [
        CommonModule,
        ChartRoutingModule,
        ChartModule,
        SharedModule
    ]
})
export class AdminChartModule {
}
