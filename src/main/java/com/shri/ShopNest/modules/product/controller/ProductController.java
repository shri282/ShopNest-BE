package com.shri.ShopNest.modules.product.controller;

import com.shri.ShopNest.modules.product.dto.ProductResponse;
import com.shri.ShopNest.modules.product.service.ProductService;
import com.shri.ShopNest.modules.product.dto.CreateProductReq;
import com.shri.ShopNest.modules.product.dto.UpdateProductReq;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(@RequestParam Map<String, String> filter) {
        return new ResponseEntity<>(productService.findAll(filter), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductResponse>> findAllPaginated(@RequestParam Map<String, String> filter) {
        return new ResponseEntity<>(
                productService.findAllPaginated(Integer.parseInt(filter.get("page")),
                        Integer.parseInt(filter.get("size"))), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> findOne(@PathVariable int id) {
        return new ResponseEntity<>(productService.findOneDto(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestPart(value = "product", required = true) CreateProductReq req,
                                                  @RequestPart(value = "image", required = false)
                                          MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.create(req, image), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductResponse> update(@Valid @RequestPart(value = "product", required = true) UpdateProductReq req,
                                                  @RequestPart(value = "image", required = false)
                                          MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.update(req, image), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        productService.delete(id);
        return new ResponseEntity<>("product deleted successfully", HttpStatus.OK);
    }

    @GetMapping("search")
    public List<ProductResponse> search(@RequestParam Map<String, String> keywords) {
        Map.Entry<String, String> entry = keywords.entrySet().iterator().next();
        String field = entry.getKey();
        String keyword = entry.getValue();
        return productService.searchWithSpecification(field, keyword);
    }


}
