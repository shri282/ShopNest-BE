package com.shri.ShopNest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shri.ShopNest.enums.CartStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "carts")
public class Cart {
    public Cart(User user, String currency, LocalDateTime expiresAt) {
        this.user = user;
        this.status = CartStatus.ACTIVE;
        this.currency = currency;
        this.taxAmount = BigDecimal.valueOf(0);
        this.shippingCost = BigDecimal.valueOf(0);
        this.discountAmount = BigDecimal.ZERO;
        this.subtotalPrice = BigDecimal.ZERO;
        this.grandTotal = BigDecimal.ZERO;
        this.totalQuantity = 0;
        this.totalItems = 0;
        this.expiresAt = expiresAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_cart_user"))
    private User user;

    private String sessionId;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    private Integer totalItems;

    private Integer totalQuantity;

    private BigDecimal subtotalPrice;

    private BigDecimal discountAmount;

    private BigDecimal taxAmount;

    private BigDecimal shippingCost;

    private BigDecimal grandTotal;

    private String currency;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private LocalDateTime expiresAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void calculateCartTotals() {
        int totalQuantity = 0;
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem item : this.getItems()) {
            totalQuantity += item.getQuantity();
            subtotal = subtotal.add(item.getTotalPrice());
        }

        this.setTotalQuantity(totalQuantity);
        this.setSubtotalPrice(subtotal);
        this.setTotalItems(this.getItems().size());
        this.setGrandTotal(subtotal);
    }

    public void addItem(CartItem cartItem) {
        this.getItems().add(cartItem);
        this.calculateCartTotals();
    }

    public void removeItem(CartItem cartItem) {
        this.getItems().removeIf(item -> item.getId().equals(cartItem.getId()));
        this.calculateCartTotals();
    }
}
