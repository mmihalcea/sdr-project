package edu.sdr.electronics.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AddProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Long category;
    private List<byte[]> files;
}
