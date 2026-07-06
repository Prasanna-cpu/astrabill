package com.spring.astrabill.backend.repository;

import com.spring.astrabill.backend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {

    @Query("SELECT s FROM Store s WHERE s.storeAdmin.id = :storeAdminId")
    Optional<Store> findByStoreAdminId(String storeAdminId);

}
