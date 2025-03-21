package com.example.E_Shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.SearchHistory;
import com.example.E_Shopping.service.SearchHistoryService;

@RestController
@RequestMapping("api/search-history")
public class SearchHistoryController{
    @Autowired
    private SearchHistoryService searchHistoryService;

    @PostMapping("/save")
    ResponseEntity<ResponseObject> saveSearchTerm(@RequestBody SearchHistory searchHistory){
        ResponseObject responseObject = searchHistoryService.saveSearchTerm(searchHistory);
        if(responseObject.getStatus().equals("Failed")){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }else return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
    @GetMapping("/historyByUserId/{userId}")
    ResponseEntity<ResponseObject> getSearchTermByUserId(@PathVariable Long userId){
        ResponseObject  responseObject = searchHistoryService.findSearchTermByUserId(userId);
        if(responseObject.getStatus().equals("Failed")){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
        }
        else return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }
}
