package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailsResponse extends OrderResponse {
    private List<OrderLineResponse> orderLines;
}
