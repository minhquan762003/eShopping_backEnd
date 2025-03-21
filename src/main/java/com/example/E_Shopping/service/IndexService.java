package com.example.E_Shopping.service;

import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@Service
public class IndexService {
    private final ElasticsearchClient   elasticsearchClient;

    public IndexService(ElasticsearchClient elasticsearchClient){
        this.elasticsearchClient = elasticsearchClient;
    }

    public void createIndex(){
        
    }
}
