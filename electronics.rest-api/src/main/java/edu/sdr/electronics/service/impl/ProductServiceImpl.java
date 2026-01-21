package edu.sdr.electronics.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import edu.sdr.electronics.domain.*;
import edu.sdr.electronics.dto.request.AddProductRequest;
import edu.sdr.electronics.dto.request.ProductReviewRequest;
import edu.sdr.electronics.dto.response.ProductCSV;
import edu.sdr.electronics.dto.response.ProductCombinedListResponse;
import edu.sdr.electronics.dto.response.ProductDetails;
import edu.sdr.electronics.dto.response.ProductItem;
import edu.sdr.electronics.repository.*;
import edu.sdr.electronics.service.ProductService;
import edu.sdr.electronics.service.RecommendationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductReviewRepository productReviewRepository;
    private final StoreUserRepository storeUserRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final StockStatusRepository stockStatusRepository;
    private final RecommendationService recommendationService;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addProduct(AddProductRequest addProductRequest) {
        Product product = modelMapper.map(addProductRequest, Product.class);
        product.setCategory(new Category(addProductRequest.getCategory()));
        product.setPhotos(new ArrayList<>());
        product.setStockStatus(new StockStatus(1));
        addProductRequest.getFiles().forEach(f -> {
            ProductPhoto photo = new ProductPhoto();
            photo.setPhoto(f);
            photo.setProduct(product);
            product.getPhotos().add(photo);
        });

        productRepository.save(product);

    }

    @Override
    public void importAllProducts() {
        try {

            File dataset = new ClassPathResource("dataset/ElectronicsData.csv").getFile();

            List<ProductCSV> productsCSV = new CsvToBeanBuilder(new FileReader(dataset))
                    .withType(ProductCSV.class)
                    .withSkipLines(1)
                    .withQuoteChar('\"')
                    .build()
                    .parse();

            List<Product> products = new ArrayList<>();

            productsCSV.forEach(p -> {
                if (p.getPrice() != null && !p.getPrice().isEmpty() && !p.getPrice().isBlank()) {
                    Category category = categoryRepository.findByName(p.getCategory()).orElseGet(() -> categoryRepository.save(new Category(p.getCategory())));
                    Product product = new Product();
                    product.setName(p.getName());
                    product.setCategory(category);
                    product.setStockStatus(stockStatusRepository.findById(1L).get());
                    product.setPrice(new BigDecimal(p.getPrice().replace("$", "").replaceAll("through.*", "").replace(",", "").trim()));
                    product.setDescription(Normalizer.normalize(p.getDescription(), Normalizer.Form.NFD));
                    product.setDiscount(p.getDiscount());
                    products.add(product);
                    log.info("INSERTED" + product.getName());
                }
            });

            productRepository.saveAll(products);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateProduct(Long productId, AddProductRequest addProductRequest) {
        Optional<Product> productContainer = productRepository.findById(productId);

        if (productContainer.isPresent()) {
            Product product = modelMapper.map(addProductRequest, Product.class);
            product.setId(productId);
            product.setCategory(new Category(addProductRequest.getCategory()));
            product.setPhotos(new ArrayList<>());
            product.setStockStatus(new StockStatus(1));
            addProductRequest.getFiles().forEach(f -> {
                ProductPhoto photo = new ProductPhoto();
                photo.setPhoto(f);
                photo.setProduct(product);
                product.getPhotos().add(photo);
            });

            productRepository.save(product);

        } else {
            throw new IllegalArgumentException("Produsul nu există");
        }
    }

    @Override
    public ProductCombinedListResponse listAllProductsWithRecommendations() {
        List<Product> products = productRepository.findAll();
        ProductCombinedListResponse response = new ProductCombinedListResponse();
        response.setProducts(products.stream().map(i -> {
            ProductItem res = modelMapper.map(i, ProductItem.class);
            res.setAverageRating(productReviewRepository.getAverageRatingByProductId(i.getId()));
            res.setPhotos(Collections.singletonList(Base64.getEncoder().encodeToString(i.getPhotos().get(0).getPhoto())));
            return res;
        }).collect(Collectors.toList()));
        response.setRecommendations(recommendationService.getHomepageRecommendations());
        return response;
    }

    @Override
    public ShoppingCartProductResponse listAllProductsForShoppingCart(List<Long> ids) {
        List<Product> products = productRepository.findAll();
        if (ids != null) {
            products = products.stream().filter(product -> ids.contains(product.getId())).collect(Collectors.toList());
        }
        List<ProductItem> cartProducts =  products.stream().map(i -> {
            ProductItem res = modelMapper.map(i, ProductItem.class);
            res.setAverageRating(productReviewRepository.getAverageRatingByProductId(i.getId()));
            res.setPhotos(Collections.singletonList(Base64.getEncoder().encodeToString(i.getPhotos().get(0).getPhoto())));
            return res;
        }).toList();

        ShoppingCartProductResponse response = new ShoppingCartProductResponse();
        response.setShoppingCartProducts(cartProducts);
        response.setAlsoBought(recommendationService.getAlsoBoughtProducts(cartProducts.get(0).getId()));
        response.setAlsoBoughtTitle(cartProducts.get(0).getName());
        return response;
    }

    @Override
    public ProductDetails getProductDetails(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            ProductDetails res = modelMapper.map(product, ProductDetails.class);
            res.setAverageRating(productReviewRepository.getAverageRatingByProductId(product.getId()));
            res.setPhotos(new ArrayList<>());
            product.getPhotos().forEach(p -> res.getPhotos().add("data:image/jpg;base64," + Base64.getEncoder().encodeToString(p.getPhoto())));
            res.setSimilarProducts(recommendationService.getSimilarProducts(productId));
            res.setAlsoBoughtProducts(recommendationService.getAlsoBoughtProducts(productId));
            return res;
        }

        return null;
    }

    @Override
    public ProductDetails reviewProduct(Long productId, ProductReviewRequest productReviewRequest) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            ProductReview review = modelMapper.map(productReviewRequest, ProductReview.class);
            review.setProduct(product);
            review.setDatePosted(LocalDateTime.now());
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            StoreUser storeUser = this.storeUserRepository.findByUsername(principal.getName()).orElse(null);
            if (storeUser != null) {
                review.setStoreUser(storeUser);
            }
            product.getReviews().add(review);
            this.productRepository.save(product);
            return this.getProductDetails(productId);
        }

        return null;
    }
}
