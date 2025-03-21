package com.example.E_Shopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.E_Shopping.model.UserAndRole;

@Repository
public interface UserAndRoleRepository extends JpaRepository<UserAndRole, Long> {
    // @Query("SELECT ur.roles FROM UserAndRole ur WHERE ur.users.userId = :userId")
    // List<Roles> findRolesByUserId(@Param("userId") Long userId);
    List<UserAndRole> findByUsersUserId(Long userId);
}