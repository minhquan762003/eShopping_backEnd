package com.example.E_Shopping.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.E_Shopping.model.ProductDocument;

@Repository
public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Integer> {
    List<ProductDocument> findByNameContaining(String keyword);

}
