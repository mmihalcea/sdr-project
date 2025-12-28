package edu.sdr.electronics.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReviewRequest {
    private String title;
    private String description;
    private Integer rating;
}
