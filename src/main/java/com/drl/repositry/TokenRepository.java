package com.drl.repositry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drl.entities.Token;


@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	List<Token> findAllValidTokenByUserId(Long id);
	Optional<Token> findByToken(String token);
	@Query(value = "delete from token where user_id=:userId" , nativeQuery = true)
	void deleteByUserId(Integer userId);
	
}

