package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.Product;
import edu.sdr.electronics.domain.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query("SELECT AVG(ir.rating) from ProductReview ir where ir.product.id=?1")
    BigDecimal getAverageRatingByProductId(Long productId);

    @Query("SELECT r.product FROM ProductReview r GROUP BY r.product ORDER BY COUNT(r) DESC")
    List<Product> findMostReviewedProducts();
}
