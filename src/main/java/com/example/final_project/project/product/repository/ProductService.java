package com.example.final_project.project.product.repository;

import com.example.final_project.project.product.repository.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProduct();
    Optional<Product> getProductById(Long id);
    void saveProduct(Product product);
    void deleteProductByID(long id);
    void updateProductPriceByComplexity(long id);
    Optional<Product> findProductById(long id);
    List<ProductDTO> getNameAndPrice(String complexity);
}
