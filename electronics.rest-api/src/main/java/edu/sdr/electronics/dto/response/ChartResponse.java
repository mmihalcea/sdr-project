package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartResponse {

    private ChartData orderStatusData;
    private ChartData productTypeData;


    public ChartResponse() {
        this.orderStatusData = new ChartData();
        this.productTypeData = new ChartData();
    }

}
