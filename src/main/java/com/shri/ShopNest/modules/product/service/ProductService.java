package com.shri.ShopNest.modules.product.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.modules.product.dto.ProductResponse;
import com.shri.ShopNest.model.Product;
import com.shri.ShopNest.repo.ProductRepo;
import com.shri.ShopNest.modules.product.specification.ProductSpecification;
import com.shri.ShopNest.modules.product.dto.CreateProductReq;
import com.shri.ShopNest.modules.product.dto.UpdateProductReq;
import com.shri.ShopNest.modules.product.mapper.ProductMapper;
import com.shri.ShopNest.model.ProductCategory;
import com.shri.ShopNest.utils.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CloudinaryService cloudinaryService;
    private final ProductCategoryService productCategoryService;

    public ProductService(ProductRepo productRepo,
                          CloudinaryService cloudinaryService,
                          ProductCategoryService productCategoryService) {
        this.productRepo = productRepo;
        this.cloudinaryService = cloudinaryService;
        this.productCategoryService = productCategoryService;
    }

    public List<ProductResponse> findAll(Map<String, String> filter) {
        if (!filter.isEmpty()) {
            return productRepo.findAll().stream()
                    .filter(product -> {
                        boolean matches = true;
                        if (filter.containsKey("name")) {
                            matches &= product.getName().equalsIgnoreCase(filter.get("name"));
                        }
                        if (filter.containsKey("category")) {
                            matches &= product.getCategory().getName().equalsIgnoreCase(filter.get("category"));
                        }
                        if (filter.containsKey("prize")) {
                            matches &= product.getPrize() == Double.parseDouble(filter.get("prize"));
                        }
                        if (filter.containsKey("newArrivals")) {
                            matches &= product.getCreatedAt().isAfter(
                                    java.time.LocalDateTime.now().minusDays(15)
                            );
                        }
                        return matches;
                    }).map(ProductMapper::toProductDto).collect(Collectors.toList());
        }
        return productRepo.findAll().stream().map(ProductMapper::toProductDto).collect(Collectors.toList());
    }

    public Page<ProductResponse> findAllPaginated(int page, int size) {
        Page<Product> productPage = productRepo.findAll(PageRequest.of(page, size));
        return productPage.map(ProductMapper::toProductDto);
    }

    public Product findOne(int id) throws ResourceNotFoundException {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }

    public ProductResponse findOneDto(int id) throws ResourceNotFoundException {
        return ProductMapper.toProductDto(findOne(id));
    }

    public ProductResponse create(CreateProductReq req, MultipartFile imageFile) throws IOException {
        Product product = ProductMapper.toProductEntity(req);

        ProductCategory productCategory = productCategoryService.findOne(req.getCategoryId());
        product.setCategory(productCategory);

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageURL = cloudinaryService.upload(imageFile);

            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageURL(imageURL);
            product.setImage(imageFile.getBytes());
        }

        return ProductMapper.toProductDto(productRepo.save(product));
    }

    public ProductResponse update(UpdateProductReq req, MultipartFile imageFile) throws IOException {
        Product product = ProductMapper.toProductEntity(req);

        ProductCategory productCategory = productCategoryService.findOne(req.getCategoryId());
        product.setCategory(productCategory);

        Product existingProduct = findOne(req.getId());

        if ((imageFile != null && !imageFile.isEmpty())) {
            byte[] newImageBytes = imageFile.getBytes();

            if (!Arrays.equals(existingProduct.getImage(), newImageBytes)) {
                String imageURL = cloudinaryService.upload(imageFile);
                product.setImageURL(imageURL);
                product.setImage(newImageBytes);
            } else {
                product.setImage(existingProduct.getImage());
                product.setImageURL(existingProduct.getImageURL());
            }

            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
        } else if (req.getImageURL() != null && !req.getImageURL().isEmpty()) {
            product.setImage(existingProduct.getImage());
            product.setImageName(existingProduct.getImageName());
            product.setImageType(existingProduct.getImageType());
            product.setImageURL(existingProduct.getImageURL());
        } else {
            product.removeImage();
        }

        return ProductMapper.toProductDto(productRepo.save(product));
    }

    public void delete(int id) throws ResourceNotFoundException {
        productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
        productRepo.deleteById(id);
    }

    public List<ProductResponse> search(String field, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be blank");
        }
        List<Product> products;

        switch (field.toLowerCase()) {
            case "all" -> {
                products = productRepo.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(keyword, keyword, keyword);
            }
            case "name" -> {
                products = productRepo.findByNameContainingIgnoreCase(keyword);
            }
            case "brand" -> {
                products = productRepo.findByBrandContainingIgnoreCase(keyword);
            }
            case "category" -> {
                products = productRepo.findByCategoryNameContainingIgnoreCase(keyword);
            }
            default -> throw new IllegalArgumentException("Invalid search field: " + field);
        }

        return products.stream().map(ProductMapper::toProductDto).toList();
    }

    public List<ProductResponse> searchWithSpecification(String field, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be blank");
        }

        Specification<Product> spec = Specification.where(null);

        switch (field.toLowerCase()) {
            case "all" ->
                    spec = spec.or(ProductSpecification.hasNameLike(keyword))
                            .or(ProductSpecification.hasBrandLike(keyword))
                            .or(ProductSpecification.hasCategoryLike(keyword));
            case "name" ->
                    spec = spec.or(ProductSpecification.hasNameLike(keyword));
            case "brand" ->
                    spec = spec.or(ProductSpecification.hasBrandLike(keyword));
            case "category" ->
                    spec = spec.or(ProductSpecification.hasCategoryLike(keyword));
            default -> throw new IllegalArgumentException("Invalid search field: " + field);
        }

        return productRepo.findAll(spec).stream().map(ProductMapper::toProductDto).toList();
    }


}
