package com.shri.ShopNest.product.controller;

import com.shri.ShopNest.product.dto.CreateProductReqDto;
import com.shri.ShopNest.product.dto.ProductDto;
import com.shri.ShopNest.product.dto.UpdateProductReqDto;
import com.shri.ShopNest.product.service.ProductService;
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
    public ResponseEntity<List<ProductDto>> findAll(@RequestParam Map<String, String> filter) {
        return new ResponseEntity<>(productService.findAll(filter), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductDto>> findAllPaginated(@RequestParam Map<String, String> filter) {
        return new ResponseEntity<>(
                productService.findAllPaginated(Integer.parseInt(filter.get("page")),
                        Integer.parseInt(filter.get("size"))), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> findOne(@PathVariable int id) {
        return new ResponseEntity<>(productService.findOneDto(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestPart(value = "product", required = true) CreateProductReqDto req,
                                          @RequestPart(value = "image", required = false)
                                          MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.create(req, image), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestPart(value = "product", required = true) UpdateProductReqDto req,
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
    public List<ProductDto> search(@RequestParam Map<String, String> keywords) {
        Map.Entry<String, String> entry = keywords.entrySet().iterator().next();
        String field = entry.getKey();
        String keyword = entry.getValue();
        return productService.searchWithSpecification(field, keyword);
    }


}
