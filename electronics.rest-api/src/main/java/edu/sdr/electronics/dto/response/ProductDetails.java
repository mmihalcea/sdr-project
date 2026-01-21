package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductDetails extends ProductItem {

    private String description;
    private List<ProductReviewResponse> reviews;
    private Set<ProductItem> similarProducts;
}
