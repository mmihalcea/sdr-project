package edu.sdr.electronics.service;

import edu.sdr.electronics.dto.response.ProductItem;

import java.util.List;
import java.util.Set;

public interface RecommendationService {

    Set<ProductItem> getSimilarProducts(Long productId);

    Set<ProductItem> getAlsoBoughtProducts(Long productId);

    List<ProductItem> getHomepageRecommendations();

    List<ProductItem> getFrequentlyBoughtTogether(Long productId);
}
