package edu.sdr.electronics.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 4096)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    private String discount;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPhoto> photos = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "stock_status_id", nullable = false)
    private StockStatus stockStatus;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> reviews;

}
