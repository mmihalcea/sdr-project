package edu.sdr.electronics.service.impl;

import edu.sdr.electronics.domain.OrderLine;
import edu.sdr.electronics.domain.Product;
import edu.sdr.electronics.dto.response.ProductItem;
import edu.sdr.electronics.repository.OrderLineRepository;
import edu.sdr.electronics.repository.ProductRepository;
import edu.sdr.electronics.repository.ProductReviewRepository;
import edu.sdr.electronics.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final ProductRepository productRepository;
    private final ProductReviewRepository productReviewRepository;
    private final OrderLineRepository orderLineRepository;
    private final ModelMapper modelMapper;

    @Override
    public Set<ProductItem> getSimilarProducts(Long productId) {
        Map<Long, Double> similarityMap = new HashMap<>();
        Product product = productRepository.findById(productId).orElse(null);
        if(product != null) {
            List<Product> allProducts = productRepository.findAll();
            allProducts.forEach(p -> {
                double sim = computeCosineSimilarity(product.getDescription(), p.getDescription());
                similarityMap.put(p.getId(), sim);
            });
            List<Map.Entry<Long, Double>> similarityResult = similarityMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();

            return similarityResult.stream().limit(5).map(res -> allProducts.stream().filter(p -> p.getId().equals(res.getKey())).map(p -> modelMapper.map(p, ProductItem.class)).findFirst().get()).collect(Collectors.toSet());
        }

        return null;
    }

    @Override
    public List<ProductItem> getAlsoBoughtProducts(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            List<OrderLine> orderLines = orderLineRepository.findByProduct(product);
            List<Long> orderIds = orderLines.stream().map(ol -> ol.getStoreOrder().getId()).collect(Collectors.toList());


            List<Product> alsoBought = orderLineRepository.findAll().stream()
                    .filter(ol -> orderIds.contains(ol.getStoreOrder().getId()))
                    .map(OrderLine::getProduct)
                    .filter(p -> !p.getId().equals(productId))
                    .distinct()
                    .limit(4)
                    .collect(Collectors.toList());

            return alsoBought.stream()
                    .limit(5)
                    .map(p -> {
                        ProductItem item = modelMapper.map(p, ProductItem.class);
                        item.setAverageRating(productReviewRepository.getAverageRatingByProductId(p.getId()));
                        return item;
                    })
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<ProductItem> getHomepageRecommendations() {
        List<Product> mostReviewedProducts = productReviewRepository.findMostReviewedProducts();
        return mostReviewedProducts.stream()
                .map(p -> {
                    ProductItem item = modelMapper.map(p, ProductItem.class);
                    item.setAverageRating(productReviewRepository.getAverageRatingByProductId(p.getId()));
                    return item;
                })
                .limit(8)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductItem> getFrequentlyBoughtTogether(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            List<OrderLine> orderLines = orderLineRepository.findByProduct(product);
            List<Long> orderIds = orderLines.stream().map(ol -> ol.getStoreOrder().getId()).collect(Collectors.toList());

            List<Product> boughtTogether = orderLineRepository.findAll().stream()
                    .filter(ol -> orderIds.contains(ol.getStoreOrder().getId()))
                    .map(OrderLine::getProduct)
                    .filter(p -> !p.getId().equals(productId))
                    .collect(Collectors.toList());

            Map<Product, Long> frequencyMap = boughtTogether.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            return frequencyMap.entrySet().stream()
                    .sorted(Map.Entry.<Product, Long>comparingByValue().reversed())
                    .limit(2)
                    .map(Map.Entry::getKey)
                    .map(p -> {
                        ProductItem item = modelMapper.map(p, ProductItem.class);
                        item.setAverageRating(productReviewRepository.getAverageRatingByProductId(p.getId()));
                        return item;
                    })
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private static double computeCosineSimilarity(String text1, String text2) {
        Map<CharSequence, Integer> vectorA = toFrequencyMap(text1);
        Map<CharSequence, Integer> vectorB = toFrequencyMap(text2);
        CosineSimilarity cosine = new CosineSimilarity();
        return cosine.cosineSimilarity(vectorA, vectorB);
    }

    private static Map<CharSequence, Integer> toFrequencyMap(String text) {
        Map<CharSequence, Integer> freq = new HashMap<>();
        text = text.replace("Product Description", "");
        String[] textParts = Normalizer.normalize(text, Normalizer.Form.NFD).toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\W+");
        for (String token : textParts) {
            if (token.isBlank()) continue;
            freq.put(token, freq.getOrDefault(token, 0) + 1);
        }
        return freq;
    }
}
