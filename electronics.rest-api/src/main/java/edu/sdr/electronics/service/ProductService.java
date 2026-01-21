package edu.sdr.electronics.service;

import edu.sdr.electronics.domain.Category;
import edu.sdr.electronics.domain.ShoppingCartProductResponse;
import edu.sdr.electronics.dto.request.AddProductRequest;
import edu.sdr.electronics.dto.request.ProductReviewRequest;
import edu.sdr.electronics.dto.response.ProductCombinedListResponse;
import edu.sdr.electronics.dto.response.ProductDetails;
import edu.sdr.electronics.dto.response.ProductItem;

import java.util.List;

public interface ProductService {

    public List<Category> getAllCategories();

    public void addProduct(AddProductRequest addProductRequest);

    public void importAllProducts();

    public void updateProduct(Long productId, AddProductRequest addProductRequest);

    public ProductCombinedListResponse listAllProductsWithRecommendations();

    public ShoppingCartProductResponse listAllProductsForShoppingCart(List<Long> ids);

    public ProductDetails getProductDetails(Long productId);

    public ProductDetails reviewProduct(Long productId, ProductReviewRequest productReviewRequest);
}
