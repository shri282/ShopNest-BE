package com.shri.ShopNest.product.service;

import com.shri.ShopNest.exception.exceptions.ResourceNotFoundException;
import com.shri.ShopNest.product.model.Product;
import com.shri.ShopNest.product.repo.ProductRepo;
import com.shri.ShopNest.utils.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.shri.ShopNest.product.specification.ProductSpecification.*;

@SuppressWarnings("unused")
@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CloudinaryService cloudinaryService;

    public ProductService(ProductRepo productRepo, CloudinaryService cloudinaryService) {
        this.productRepo = productRepo;
        this.cloudinaryService = cloudinaryService;
    }

    public List<Product> findAll(Map<String, String> filter) {
        if (!filter.isEmpty()) {
            return productRepo.findAll().stream()
                    .filter(product -> {
                        boolean matches = true;
                        if (filter.containsKey("name")) {
                            matches &= product.getName().equalsIgnoreCase(filter.get("name"));
                        }
                        if (filter.containsKey("category")) {
                            matches &= product.getCategory().equalsIgnoreCase(filter.get("category"));
                        }
                        if (filter.containsKey("prize")) {
                            matches &= product.getPrize() == Double.parseDouble(filter.get("prize"));
                        }
                        return matches;
                    })
                    .collect(Collectors.toList());
        }
        return productRepo.findAll();
    }

    public Page<Product> findAllPaginated(int page, int size) {
        return productRepo.findAll(PageRequest.of(page, size));
    }

    public Product findOne(int id) throws ResourceNotFoundException {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
    }

    public Product create(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageURL = cloudinaryService.upload(imageFile);

            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
//            product.setImage(imageFile.getBytes());
            product.setImageURL(imageURL);
        }

        return productRepo.save(product);
    }

    public Product update(Product product, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            Product existingProduct = findOne(product.getId());
//            if (!Arrays.equals(existingProduct.getImage(), imageFile.getBytes())) {
                String imageURL = cloudinaryService.upload(imageFile);
                product.setImageURL(imageURL);
//            }

            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
//            product.setImage(imageFile.getBytes());
        } else {
            product.removeImage();
        }

        return productRepo.save(product);
    }

    public void delete(int id) throws ResourceNotFoundException {
        productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
        productRepo.deleteById(id);
    }

    public List<Product> search(String field, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be blank");
        }

        switch (field.toLowerCase()) {
            case "all" -> {
                return productRepo.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword, keyword);
            }
            case "name" -> {
                return productRepo.findByNameContainingIgnoreCase(keyword);
            }
            case "brand" -> {
                return productRepo.findByBrandContainingIgnoreCase(keyword);
            }
            case "category" -> {
                return productRepo.findByCategoryContainingIgnoreCase(keyword);
            }
            default -> throw new IllegalArgumentException("Invalid search field: " + field);
        }
    }

    public List<Product> searchWithSpecification(String field, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword must not be blank");
        }

        Specification<Product> spec = Specification.where(null);

        switch (field.toLowerCase()) {
            case "all" ->
                    spec = spec.or(hasNameLike(keyword))
                            .or(hasBrandLike(keyword))
                            .or(hasCategoryLike(keyword));
            case "name" ->
                    spec = spec.or(hasNameLike(keyword));
            case "brand" ->
                    spec = spec.or(hasBrandLike(keyword));
            case "category" ->
                    spec = spec.or(hasCategoryLike(keyword));
            default -> throw new IllegalArgumentException("Invalid search field: " + field);
        }

        return productRepo.findAll(spec);
    }


}
