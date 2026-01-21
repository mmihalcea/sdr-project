package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.OrderLine;
import edu.sdr.electronics.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findByProduct(Product product);
}
