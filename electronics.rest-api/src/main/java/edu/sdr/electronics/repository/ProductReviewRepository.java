package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query("SELECT AVG(ir.rating) from ProductReview ir where ir.product.id=?1")
    BigDecimal getAverageRatingByProductId(Long productId);
}
