package com.example.E_Shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.example.E_Shopping.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    public Optional<Users> findByUsername(String username);
    public Optional<Users> findByEmail(String email);
}
