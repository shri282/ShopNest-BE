package com.shri.ShopNest.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug; // SEO-friendly URL part

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ProductCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ProductCategory> children;

    private Integer level; // For category depth (0=root, 1=sub, etc.)

    private String path;   // Example: "1/3/15"

    private Boolean isLeaf; // True if no subcategories

    private Boolean isActive;

    private Integer displayOrder;

    private String imageUrl;

    private String iconUrl;

    // SEO fields
    private String metaTitle;

    @Column(length = 500)
    private String metaDescription;

    @Column(length = 500)
    private String metaKeywords;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
