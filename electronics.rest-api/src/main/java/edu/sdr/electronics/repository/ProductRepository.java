package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop3ByCategoryIdOrderByPriceAsc(Long categoryId);
}
