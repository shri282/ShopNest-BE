package com.shri.ShopNest.modules.product.service;

import com.shri.ShopNest.modules.product.dto.CreateProductReviewReq;
import com.shri.ShopNest.modules.product.dto.ProductReviewStatsResponse;
import com.shri.ShopNest.model.Product;
import com.shri.ShopNest.repo.ProductRepo;
import com.shri.ShopNest.modules.product.dto.ProductReviewResponse;
import com.shri.ShopNest.modules.product.mapper.ProductReviewMapper;
import com.shri.ShopNest.model.ProductReview;
import com.shri.ShopNest.repo.ProductReviewRepo;
import com.shri.ShopNest.model.User;
import com.shri.ShopNest.repo.UserRepo;
import com.shri.ShopNest.utils.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductReviewService {
    private final ProductReviewRepo productReviewRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CloudinaryService cloudinaryService;

    public ProductReviewService(ProductReviewRepo productReviewRepo,
                                UserRepo userRepo,
                                ProductRepo productRepo,
                                CloudinaryService cloudinaryService) {
        this.productReviewRepo = productReviewRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.cloudinaryService = cloudinaryService;
    }
    public List<ProductReviewResponse> findAll(int productId) {
        return productReviewRepo.findByProductId(productId).stream().map(ProductReviewMapper::toProductReviewDto).toList();
    }

    public ProductReviewResponse create(int productId,
                                        Long reviewerId,
                                        CreateProductReviewReq productReviewData,
                                        List<MultipartFile> medias) throws IOException {
        ProductReview productReview = ProductReviewMapper.toProductReviewEntity(productReviewData);

        User reviewer = userRepo.getReferenceById(reviewerId);
        Product product = productRepo.getReferenceById(productId);
        productReview.setProduct(product);
        productReview.setReviewer(reviewer);

        StringBuilder mediaUrls = new StringBuilder();

        if (medias != null && !medias.isEmpty()) {
            for (int i = 0; i < medias.size(); i++) {
                String url = cloudinaryService.upload(medias.get(i));

                if (i > 0) {
                    mediaUrls.append(",");
                }
                mediaUrls.append(url);
            }
        }

        productReview.setMediaUrls(mediaUrls.toString());
        return ProductReviewMapper.toProductReviewDto(productReviewRepo.save(productReview));
    }

    public ProductReviewStatsResponse getReviewStats(int productId) {
        List<ProductReview> reviews = productReviewRepo.findByProductId(productId);

        long total = reviews.size();
        if (total == 0) {
            return ProductReviewStatsResponse.builder()
                    .averageRating(0.0)
                    .totalRatings(0)
                    .counts(Map.of(1,0L,2,0L,3,0L,4,0L,5,0L))
                    .percentages(Map.of(1,0.0,2,0.0,3,0.0,4,0.0,5,0.0))
                    .build();
        }

        double avg = reviews.stream()
                .mapToDouble(ProductReview::getRating)
                .average()
                .orElse(0.0);

        Map<Integer, Long> counts = reviews.stream()
                .collect(Collectors.groupingBy(
                        r -> Math.round(r.getRating()),
                        Collectors.counting()
                ));

        // ensure all 1–5 stars are present
        for (int i = 1; i <= 5; i++) {
            counts.putIfAbsent(i, 0L);
        }

        Map<Integer, Double> percentages = counts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (e.getValue() * 100.0) / total
                ));

        return ProductReviewStatsResponse.builder()
                .averageRating(Math.round(avg * 10.0) / 10.0) // round to 1 decimal
                .totalRatings(total)
                .counts(counts)
                .percentages(percentages)
                .build();
    }
}
