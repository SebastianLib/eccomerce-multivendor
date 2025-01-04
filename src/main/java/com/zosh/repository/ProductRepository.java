package com.zosh.repository;

import com.zosh.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Long categoryId);;

    @Query("SELECT SUM(p.price * p.quantity) FROM Product p")
    Double findTotalCost();

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);
}
