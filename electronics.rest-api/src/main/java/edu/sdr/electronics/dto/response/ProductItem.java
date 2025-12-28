package edu.sdr.electronics.dto.response;

import edu.sdr.electronics.domain.Category;
import edu.sdr.electronics.domain.StockStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductItem {

    private Long id;
    private String name;
    private BigDecimal price;
    private Category category;
    private List<String> photos;
    private StockStatus stockStatus;
    private BigDecimal averageRating;
}
