package edu.sdr.electronics.domain;

import edu.sdr.electronics.dto.response.ProductItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingCartProductResponse {
    List<ProductItem> shoppingCartProducts;
    List<ProductItem> alsoBought;
    String alsoBoughtTitle;
}
