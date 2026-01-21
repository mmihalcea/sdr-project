package edu.sdr.electronics.repository;

import edu.sdr.electronics.domain.Product;
import edu.sdr.electronics.domain.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Long> {
    @Query("""
  select p
  from Product p
  where not exists (
    select 1 from ProductPhoto pp where pp.product = p
  )
""")
    List<Product> findProductsWithoutPhotos();
}