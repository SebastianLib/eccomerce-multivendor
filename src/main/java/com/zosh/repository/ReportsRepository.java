package com.zosh.repository;

import com.zosh.model.Reports;
import com.zosh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
    @Override
    List<Reports> findAll();

    List<Reports> findByUser(User user);

    Page<Reports> findByUser(User user, Pageable pageable);

    @Query("SELECT DISTINCT u FROM User u JOIN u.reports r JOIN r.products p JOIN p.category c WHERE c.id = :categoryId")
    List<User> findUsersByProductCategory(@Param("categoryId") Long categoryId);


}
