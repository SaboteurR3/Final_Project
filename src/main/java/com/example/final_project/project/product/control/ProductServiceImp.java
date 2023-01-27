package com.example.final_project.project.product.control;

import com.example.final_project.project.product.repository.ProductDTO;
import com.example.final_project.exceptions.IncorrectInputException;
import com.example.final_project.project.product.repository.entity.Product;
import com.example.final_project.project.product.repository.ProductRepository;
import com.example.final_project.project.product.repository.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
    ProductRepository productRepository;
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new IncorrectInputException("IncorrectInput!");
        }
        return productRepository.findById(id);
    }

    @Override
    public void saveProduct(Product product) {
        Product product1 = new Product(
                product.getProduct_name(),
                product.getComplexity(),
                product.getPrice()
        );
        productRepository.save(product1);
    }

    @Override
    public void deleteProductByID(long id) {
        Optional<Product> productById = productRepository.findById(id);
        if (id < 0 || productById.isEmpty()) {
            throw new IncorrectInputException("Something went wrong!");
        }
        productRepository.deleteById(id);
    }

    @Override
    public void updateProductPriceByComplexity(long id) {
        Optional<Product> productByName = productRepository.findProductById(id);
        if (productByName.isEmpty()) {
            throw new IncorrectInputException("Something went wrong");
        }
        productRepository.updateProductByComplexity(id);
    }

    @Override
    public Optional<Product> findProductById(long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public List<ProductDTO> getNameAndPrice(String complexity) {
        return productRepository.findProductByComplexity(complexity);
    }

}
