import {Component, OnInit} from '@angular/core';
import {ChartResponse} from "./chart-response.model";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss']
})
export class ChartComponent implements OnInit {
  chartData: ChartResponse = new ChartResponse();
  pieChartData: any;
  barChartData: any;

  constructor(private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.chartData = data.chartData;

      this.pieChartData = {
        labels: this.chartData.orderStatusData.labels,
        datasets: [
          {
            data: this.chartData.orderStatusData.values,
            backgroundColor: [
              "#42A5F5",
              "#66BB6A",
              "#FFA726"
            ],
            hoverBackgroundColor: [
              "#64B5F6",
              "#81C784",
              "#FFB74D"
            ]
          }
        ]
      };


      this.barChartData = {
        labels: this.chartData.instrumentTypeData.labels,
        datasets: [
          {
            label: 'Număr',
            backgroundColor: '#42A5F5',
            data: this.chartData.instrumentTypeData.values
          }
        ]
      };

    });
  }

}
