package com.shri.ShopNest.controller;

import com.shri.ShopNest.model.Product;
import com.shri.ShopNest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(@RequestParam Map<String, String> filter) {
        return new ResponseEntity<>(productService.findAll(filter), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> findAllPaginated(@RequestParam Map<String, String> filter) {
        return new ResponseEntity<>(
                productService.findAllPaginated(Integer.parseInt(filter.get("page")),
                        Integer.parseInt(filter.get("size"))), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findOne(@PathVariable int id) throws Exception {
        return new ResponseEntity<>(productService.findOne(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestPart Product product,
                                          @RequestPart(value = "image", required = false)
                                          MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.create(product, image), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestPart Product product,
                                          @RequestPart(value = "image", required = false)
                                          MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.update(product, image), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
        productService.delete(id);
        return new ResponseEntity<>("product deleted successfully", HttpStatus.OK);
    }

    @GetMapping("search")
    public List<Product> search(@RequestParam Map<String, String> keywords) {
        Map.Entry<String, String> entry = keywords.entrySet().iterator().next();
        String field = entry.getKey();
        String keyword = entry.getValue();
        return productService.searchWithSpecification(field, keyword);
    }


}
