package edu.sdr.electronics.dto.response;

import com.opencsv.bean.CsvBindByPosition;

public class ProductCSV {

    @CsvBindByPosition(position = 0)
    private String category;

    @CsvBindByPosition(position = 1)
    private String price;

    @CsvBindByPosition(position = 2)
    private String discount;

    @CsvBindByPosition(position = 4)
    private String name;

    @CsvBindByPosition(position = 6)
    private String description;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
