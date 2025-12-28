package edu.sdr.electronics.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizCompletionResponse {
    private List<String> categories;
    private List<ProductItem> products;

    public QuizCompletionResponse() {
        categories = new ArrayList<>();
        products = new ArrayList<>();
    }
}
