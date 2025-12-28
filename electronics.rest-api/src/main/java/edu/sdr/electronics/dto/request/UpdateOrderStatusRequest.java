package edu.sdr.electronics.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    private Long orderId;
    private Integer orderStatus;

}
