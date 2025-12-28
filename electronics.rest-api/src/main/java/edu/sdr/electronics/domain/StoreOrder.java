package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class StoreOrder {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "storeOrder", cascade = CascadeType.ALL)
    @NotEmpty
    private List<OrderLine> lines;
    private LocalDateTime orderDate;

    private BigDecimal priceTotal;
    @ManyToOne
    @JoinColumn(name = "store_user_id", nullable = false)
    private StoreUser storeUser;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StoreOrderStatus orderStatus;
}
