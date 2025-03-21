package com.example.E_Shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import com.example.E_Shopping.model.ProductDocument;
import com.example.E_Shopping.model.Products;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.repository.ProductDocumentRepository;
import com.example.E_Shopping.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDocumentRepository productDocumentRepository;
    

    @GetMapping("/getAllProducts")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    // @GetMapping("/search")
    // public List<Products> searchProducts(@RequestParam("name") String name) {
    //     return productService.searchProductsByName(name);
    // }

    @PostMapping("/addProduct")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ResponseObject> saveProduct(@RequestBody Products product) {
        ResponseObject responseObject = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    public ResponseEntity<ResponseObject> deleteProductById(@PathVariable Long id) {
        boolean exist = productService.existById(id);
        if (exist) {
            ResponseObject responseObject = productService.deleteProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Khong co san pham = " + id + " de xoa", ""));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    ResponseEntity<ResponseObject> updateProductById(@RequestBody Products newProduct, @PathVariable Long id) {
        ResponseObject responseObject = productService.updateStudentById(newProduct, id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @GetMapping("/category/{category}")
    ResponseEntity<ResponseObject> getProductsByCategory(@PathVariable String category) {
        ResponseObject responseObject = productService.getProductByCategory(category);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        ResponseObject responseObject = productService.getProductById(id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @PostMapping("/sync")
    public String syncProducts() {
        productService.syncProductsToElasticsearch();
        return "Products synced to Elasticsearch!";
    }

    // Tìm kiếm sản phẩm theo tên
    @GetMapping("/search")
    public List<ProductDocument> searchProducts(@RequestParam String keyword) {
        return productDocumentRepository.findByNameContaining(keyword);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam String prefix) throws IOException{
        List<String> suggestions = productService.autocomplete(prefix);
        return ResponseEntity.ok(suggestions);
    }

}
