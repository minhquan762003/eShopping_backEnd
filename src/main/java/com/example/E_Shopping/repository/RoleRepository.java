package com.example.E_Shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.E_Shopping.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long>{
    public Roles findByRoleName(String roleName);
}
