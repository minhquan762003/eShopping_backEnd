package com.example.E_Shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.E_Shopping.model.Products;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.repository.ProductRepository;
import java.util.*;

@Service
public class ProductService implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Autowired
    ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Products> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    public ResponseObject saveProduct(Products product) {
        Products savedProduct = productRepository.save(product);
        return new ResponseObject("ok", "Them san pham thanh cong", savedProduct);
    }

    public ResponseObject getProductById(Long id) {
        Optional<Products> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return new ResponseObject("ok", "Tim san pham thanh cong", foundProduct);
        }
        return new ResponseObject("Failed", "Khong tim thay san pham", "");
    }

    public ResponseObject deleteProductById(Long id) {
        boolean exist = productRepository.existsById(id);
        if (exist) {
            productRepository.deleteById(id);
            return new ResponseObject("ok", "Xoa san pham thanh cong", getProductById(id));
        }

        return new ResponseObject("Failed", "Khong tim duoc san pham id = " + id + " de xoa ", "");
    }

    public ResponseObject updateStudentById(Products newProduct, Long id) {
        if (existById(id)) {
            Products updatedProduct = productRepository.findById(id).map(
                    product -> {
                        product.setName(newProduct.getName());
                        product.setDescription(product.getDescription());
                        product.setPrice(product.getPrice());
                        product.setStock(product.getStock());
                        return productRepository.save(product);
                    }).orElseGet(() -> {
                        return productRepository.save(newProduct);
                    });

            return new ResponseObject("ok", "Sua thong tin san pham thanh cong", updatedProduct);
        } else {
            return new ResponseObject("Failed", "Sua khong thanh cong", "");
        }

    }

    public boolean existById(Long id) {
        return this.productRepository.existsById(id);
    }

}
