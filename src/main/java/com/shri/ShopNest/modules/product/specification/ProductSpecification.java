package com.shri.ShopNest.modules.product.specification;

import com.shri.ShopNest.modules.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasNameLike(String keyword) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Product> hasBrandLike(String keyword) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("brand")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Product> hasCategoryLike(String keyword) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("categoryName")), "%" + keyword.toLowerCase() + "%");
    }
}
