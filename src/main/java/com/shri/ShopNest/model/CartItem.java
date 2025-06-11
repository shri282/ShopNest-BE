package com.shri.ShopNest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "cart_items",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cart_product",
                        columnNames = {"cart_id", "product_id"}
                )
        }
)
public class CartItem {
    public CartItem(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
        this.setUnitPrice(BigDecimal.valueOf(product.getPrize()));
        this.setQuantity(1);
        this.setTotalPrice(BigDecimal.valueOf(product.getPrize()));
        this.setDiscount(BigDecimal.ZERO);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    private Long variantId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal discount;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(columnDefinition = "TEXT")
    private String customAttributes;

    private LocalDateTime addedAt;

    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        this.updatedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (unitPrice != null && quantity != null && discount != null) {
            totalPrice = unitPrice.subtract(discount).multiply(BigDecimal.valueOf(quantity));
        }
    }

    public void addQuantity(int quantity) {
        double unitPrice = this.getUnitPrice().doubleValue();
        int oldQuantity = this.getQuantity();
        double totalPrice = this.getTotalPrice().doubleValue();
        this.setTotalPrice(BigDecimal.valueOf(totalPrice + (quantity * unitPrice)));
        this.setQuantity(oldQuantity + quantity);
    }

}
