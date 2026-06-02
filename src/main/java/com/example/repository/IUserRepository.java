package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	//@Query(value = "from User where username = :username")
	Optional<User> findByuserName(String userName); // bu fonksiyon User tablosundan Username'i otomatik olarak çekebilir, Query anatasyonuna gerek yok
	
}
