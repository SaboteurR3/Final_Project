package com.example.final_project.project.product.repository;

import com.example.final_project.project.product.repository.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query(value = "update Product u set u.price= 300 where u.id = :id")
    void updateProductByComplexity(@Param("id") long id);

    @Query(value = "select u from Product u where u.id = :id")
    Optional<Product> findProductById(@Param("id") long id);

    // ProductDTO --> find product_name and price By Complexity
    @Query("Select new com.example.final_project.project.product.repository.ProductDTO" +
            "(u.product_name, u.price) from Product u where u.complexity = :complexity")
    List<ProductDTO> findProductByComplexity(@Param("complexity") String complexity);
}
