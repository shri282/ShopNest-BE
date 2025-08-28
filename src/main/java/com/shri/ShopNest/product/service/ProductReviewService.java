package com.shri.ShopNest.product.service;

import com.shri.ShopNest.product.dto.CreateProductReviewReq;
import com.shri.ShopNest.product.dto.ProductReviewResponse;
import com.shri.ShopNest.product.mapper.ProductReviewMapper;
import com.shri.ShopNest.product.model.Product;
import com.shri.ShopNest.product.model.ProductReview;
import com.shri.ShopNest.product.repo.ProductRepo;
import com.shri.ShopNest.product.repo.ProductReviewRepo;
import com.shri.ShopNest.user.model.User;
import com.shri.ShopNest.user.repo.UserRepo;
import com.shri.ShopNest.utils.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
        StringBuilder mediaUrls = new StringBuilder();

        if (!medias.isEmpty()) {
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
}
