package com.example.E_Shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.SearchHistory;
import com.example.E_Shopping.repository.SearchHistoryRepository;

@Service
public class SearchHistoryService {
    @Autowired
    SearchHistoryRepository searchHistoryRepository;

    public ResponseObject saveSearchTerm(SearchHistory searchHistory){
        Optional<SearchHistory> foundHistory = searchHistoryRepository.findBySearchTerm(searchHistory.getSearchTerm());
        if (foundHistory.isPresent()) {
            return new ResponseObject("Failed", "Lich su tim kiem da ton tai","");
        }else{
            return new ResponseObject("ok", "Them lich su tim kiem thanh cong", searchHistoryRepository.save(searchHistory));
        }
    }
    public ResponseObject findSearchTermByUserId(Long userId){
        List<SearchHistory> foundSearchHistory = searchHistoryRepository.findByUserUserId(userId);
        if (foundSearchHistory.isEmpty()) {
            return new ResponseObject("Failed", "Nguoi dung id: " + userId + " khong co san pham nao trong lich su", "");
        }else{
            return new ResponseObject("ok", "Tim lich su cua nguoi dung id: "+ userId +" thanh cong", foundSearchHistory);
        }
    }
}
