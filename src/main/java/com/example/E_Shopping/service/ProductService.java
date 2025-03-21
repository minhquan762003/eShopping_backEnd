package com.example.E_Shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.stereotype.Service;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.E_Shopping.model.ProductDocument;
import com.example.E_Shopping.model.Products;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.repository.ProductDocumentRepository;
import com.example.E_Shopping.repository.ProductRepository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements WebMvcConfigurer {

    private final ElasticsearchClient elasticsearchClient;

    public ProductService(ElasticsearchClient elasticsearchClient){
        this.elasticsearchClient = elasticsearchClient;
    }

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDocumentRepository productDocumentRepository;

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
    public ResponseObject getProductByCategory(String category){
        List<Products> foundProducts = productRepository.findByCategory(category);
        if (foundProducts.isEmpty()) {
            return new ResponseObject("Failed", "Khong tim duoc san pham nao theo category ", "");
        }
        return new ResponseObject("ok", "Tim san pham theo category thanh cong", foundProducts);
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

    public void syncProductsToElasticsearch() {
        List<Products> products = productRepository.findAll();
        products.forEach(product -> {
            ProductDocument productDocument = new ProductDocument();
            productDocument.setProductId(product.getProductId());
            productDocument.setName(product.getName());
            productDocument.setDescription(product.getDescription());
            productDocument.setPrice(product.getPrice());
            productDocument.setStock(product.getStock());
            productDocument.setImageUrl(product.getImageUrl());
            productDocument.setCategory(product.getCategory());

            // productDocument.setSuggest(new Completion(new String[]{product.getName()}));

            productDocumentRepository.save(productDocument);
        });
    
    }

    public List<String> autocomplete(String prefix) throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s
            .suggest(sug -> sug
                .suggesters("product-suggest", sg -> sg
                    .prefix(prefix)
                    .completion(c -> c
                        .field("name.suggest")
                        .size(5)
                    )
                )
            )
        );

        SearchResponse<Void> response = elasticsearchClient.search(searchRequest, Void.class);

        return response.suggest().get("product-suggest").stream()
            .flatMap(s -> s.completion().options().stream())
            .map(opt -> opt.text())
            .collect(Collectors.toList());
    }
    

}
