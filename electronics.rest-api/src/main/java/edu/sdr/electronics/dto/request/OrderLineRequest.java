package edu.sdr.electronics.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderLineRequest {
    private Long productId;
    private Integer quantity;
    private Integer daysRented;

}
