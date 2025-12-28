package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.StoreOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreOrderStatusRepository extends JpaRepository<StoreOrderStatus, Integer> {
}
