package exercise.specification;

import exercise.model.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component
public class ProductSpecification {

    public Specification<Product> build(ProductParamsDTO dto) {
        return withCategory(dto.getCategoryId()).and(withPriceGreaterThan(dto.getPriceGt())).and(withPriceLessThan(dto.getPriceLt())).and(withNameWithSubstring(dto.getTitleCont())).and(withRatingGreaterThan(dto.getRatingGt()));
    }

    private Specification<Product> withCategory(Long categoryId) {
        return ((root, query, criteriaBuilder) -> categoryId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("category").get("id"), categoryId));
    }

    private Specification<Product> withPriceGreaterThan(Integer price) {
        return ((root, query, criteriaBuilder) -> price == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("price"), price));
    }

    private Specification<Product> withPriceLessThan(Integer price) {
        return ((root, query, criteriaBuilder) -> price == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThan(root.get("price"), price));
    }

    private Specification<Product> withRatingGreaterThan(Double rating) {
        return ((root, query, criteriaBuilder) -> rating == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("rating"), rating));
    }

    private Specification<Product> withNameWithSubstring(String substring) {
        return ((root, query, criteriaBuilder) -> substring == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("title"), substring));
    }
}
// END
