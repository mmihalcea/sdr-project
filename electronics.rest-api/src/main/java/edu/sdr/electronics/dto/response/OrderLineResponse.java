package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class OrderLineResponse {
    private Integer quantity;
    private Integer daysRented;
    private BigDecimal price;
    private ProductItem product;
}
