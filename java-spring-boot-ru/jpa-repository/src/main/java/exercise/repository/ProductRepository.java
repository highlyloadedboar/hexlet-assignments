package exercise.repository;

import exercise.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //BEGIN
    List<Product> findByPriceGreaterThanAndPriceLessThan(Integer priceIsGreaterThan, Integer priceIsLessThan, Sort sort);
    // END
}
