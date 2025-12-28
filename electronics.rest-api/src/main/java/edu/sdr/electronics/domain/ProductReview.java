package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Max(5)
    @Min(1)
    @Column(nullable = false)
    private Integer rating;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDateTime datePosted;
    @ManyToOne
    @JoinColumn(name = "store_user_id", nullable = false)
    private StoreUser storeUser;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
