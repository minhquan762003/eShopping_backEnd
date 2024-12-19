package com.example.E_Shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.*;

import com.example.E_Shopping.model.Products;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.SearchRequest;
import com.example.E_Shopping.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<Products> searchProducts(@RequestParam("name") String name) {
        return productService.searchProductsByName(name);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ResponseObject> saveProduct(@RequestBody Products product) {
        ResponseObject responseObject = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteUserById(@PathVariable Long id) {
        boolean exist = productService.existById(id);
        if (exist) {
            ResponseObject responseObject = productService.deleteProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Khong co san pham = " + id + " de xoa", ""));
    }

    @PutMapping
    ResponseEntity<ResponseObject> updateProductById(@RequestBody Products newProduct, @PathVariable Long id) {
        ResponseObject responseObject = productService.updateStudentById(newProduct, id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        ResponseObject responseObject = productService.getProductById(id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

}
