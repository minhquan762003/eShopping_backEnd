package com.example.E_Shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.UserAndRole;
import com.example.E_Shopping.repository.UserAndRoleRepository;

@Service
public class UserAndRoleService {
    @Autowired
    private UserAndRoleRepository userAndRoleRepository;

    public ResponseObject saveUserAndRoleService(UserAndRole userAndRole) {
        try {
            UserAndRole savedUserAndRole = userAndRoleRepository.save(userAndRole);
            return new ResponseObject("ok", "Lưu khóa ngoại user and role thành công", savedUserAndRole);
        } catch (Exception e) {
            return new ResponseObject("failed", "Lưu khóa ngoại user and role not thành công", "");
        }
    }
}
