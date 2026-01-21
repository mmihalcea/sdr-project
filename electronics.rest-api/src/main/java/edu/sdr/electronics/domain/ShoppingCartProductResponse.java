package edu.sdr.electronics.domain;

import edu.sdr.electronics.dto.response.ProductItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ShoppingCartProductResponse {
    List<ProductItem> shoppingCartProducts;
    Set<ProductItem> alsoBought;
    String alsoBoughtTitle;
}
