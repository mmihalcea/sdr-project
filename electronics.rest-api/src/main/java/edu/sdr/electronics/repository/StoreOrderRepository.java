package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.StoreOrder;
import edu.sdr.electronics.utils.LabelValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreOrderRepository extends JpaRepository<StoreOrder, Long> {

    List<StoreOrder> findAllByStoreUserIdOrderByOrderDateDesc(Long userId);


    @Query("SELECT new edu.sdr.electronics.utils.LabelValue(c.orderStatus.value, COUNT(c.orderStatus.value)) "
            + "FROM StoreOrder AS c GROUP BY c.orderStatus.value")
    List<LabelValue> countOrdersByStatus();

    @Query("SELECT new edu.sdr.electronics.utils.LabelValue(p.category.name, COUNT(p.category.name)) "
            + "FROM Product AS p inner join OrderLine ol on ol.product.id = p.id GROUP BY p.category.name")
    List<LabelValue> countOrdersByCategory();
}
