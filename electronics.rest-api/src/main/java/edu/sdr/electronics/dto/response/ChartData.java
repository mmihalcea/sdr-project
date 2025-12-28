package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChartData {

    private List<String> labels;
    private List<Long> values;

    public ChartData() {
        this.labels = new ArrayList<>();
        this.values = new ArrayList<>();
    }
}
