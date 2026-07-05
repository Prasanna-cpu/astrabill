package com.spring.astrabill.backend.repository;

import com.spring.astrabill.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.phone = ?1")
    Optional<User> findByPhone(String phone);

    @Query("select case when count(u) > 0 then true else false end from User u where u.email = ?1")
    Boolean existsByEmail(String email);
}
