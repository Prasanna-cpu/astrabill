package com.spring.astrabill.backend.repository;

import com.spring.astrabill.backend.dto.ProductDTO;
import com.spring.astrabill.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("select p from Product p where p.store.id = ?1")
    List<Product> findByStoreId(String storeId);

    @Query("select p from Product p where p.store.id = :storeId and " +
            "(" +
            "lower(p.name) like lower(concat('%', :query ,'%')) or " +
            "lower(p.brand) like lower(concat('%', :query ,'%')) or " +
            "lower(p.stockKeepingUnit) like lower(concat('%', :query ,'%'))" +
            ")"
    )
    List<Product> searchByKeyword(@Param("query") String query, @Param("storeId") String storeId);



}
