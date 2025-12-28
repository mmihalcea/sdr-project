package edu.sdr.electronics.dto.response;

import edu.sdr.electronics.dto.request.ProductReviewRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductReviewResponse extends ProductReviewRequest {
    private LocalDateTime datePosted;
    private StoreUserResponse user;

}
