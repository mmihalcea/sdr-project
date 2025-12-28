package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetails extends ProductItem {

    private String description;
    private List<ProductReviewResponse> reviews;
}
