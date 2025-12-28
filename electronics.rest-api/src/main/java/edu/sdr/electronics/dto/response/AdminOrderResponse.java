package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminOrderResponse extends OrderResponse {
    private OrderUserResponse user;
}
