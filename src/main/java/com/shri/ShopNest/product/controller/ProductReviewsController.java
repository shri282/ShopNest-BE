package com.shri.ShopNest.product.controller;

import com.shri.ShopNest.product.dto.CreateProductReviewReq;
import com.shri.ShopNest.product.dto.ProductReviewResponse;
import com.shri.ShopNest.product.service.ProductReviewService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("products/{productId}/reviews")
public class ProductReviewsController {
    private final ProductReviewService productReviewService;

    public ProductReviewsController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @GetMapping
    public ResponseEntity<List<ProductReviewResponse>> findAll(@PathVariable("productId") int productId) {
        return ResponseEntity.ok(productReviewService.findAll(productId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductReviewResponse> create(
            @PathVariable("productId") int productId,
            @RequestParam("reviewerId") Long reviewerId,
            @RequestPart("review") CreateProductReviewReq productReview,
            @RequestPart(value = "media", required = false) List<MultipartFile> medias) throws IOException {

        return ResponseEntity.ok(productReviewService.create(productId, reviewerId, productReview, medias));
    }
}
