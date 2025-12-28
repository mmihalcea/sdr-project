package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Integer quantity;
    private Integer daysRented;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private StoreOrder storeOrder;

}
