package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCombinedListResponse {
    List<ProductItem> products;
    List<ProductItem> recommendations;
}
