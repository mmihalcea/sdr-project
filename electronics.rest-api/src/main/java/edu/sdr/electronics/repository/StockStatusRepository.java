package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockStatusRepository extends JpaRepository<StockStatus, Long> {
}
