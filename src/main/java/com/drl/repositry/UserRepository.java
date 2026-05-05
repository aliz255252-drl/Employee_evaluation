package com.drl.repositry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drl.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    @Query("SELECT u FROM User u WHERE u.txtUserName = :username")
    Optional<User> findByTxtUserName(@Param("username") String username);
    
    @Query("SELECT u FROM User u WHERE u.txtUserName = :username AND u.blnStatus = true AND u.blnUserStatus = true")
    Optional<User> findActiveUserByUsername(@Param("username") String username);
    
    @Query("SELECT u FROM User u WHERE u.serUserId = :userId")
    Optional<User> findBySerUserId(@Param("userId") Integer userId);
    
    boolean existsByTxtUserName(String username);
}
