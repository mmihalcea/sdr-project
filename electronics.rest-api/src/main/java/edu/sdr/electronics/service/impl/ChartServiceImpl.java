package edu.sdr.electronics.service.impl;

import edu.sdr.electronics.dto.response.ChartResponse;
import edu.sdr.electronics.repository.StoreOrderRepository;
import edu.sdr.electronics.service.ChartService;
import edu.sdr.electronics.utils.LabelValue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ChartServiceImpl implements ChartService {
    private final StoreOrderRepository storeOrderRepository;

    @Override
    public ChartResponse getChartData() {
        ChartResponse response = new ChartResponse();
        List<LabelValue> orderStatusData = storeOrderRepository.countOrdersByStatus();
        List<LabelValue> productCategoryData = storeOrderRepository.countOrdersByCategory();

        orderStatusData.forEach(d -> {
            response.getOrderStatusData().getLabels().add(d.getLabel());
            response.getOrderStatusData().getValues().add(d.getValue());
        });

        productCategoryData.forEach(d -> {
            response.getProductTypeData().getLabels().add(d.getLabel());
            response.getProductTypeData().getValues().add(d.getValue());
        });

        return response;
    }
}
