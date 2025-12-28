package edu.sdr.electronics.dto.response;

import edu.sdr.electronics.domain.StoreOrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {

    private Long id;
    private BigDecimal priceTotal;
    private LocalDateTime orderDate;
    private StoreOrderStatus orderStatus;

}
