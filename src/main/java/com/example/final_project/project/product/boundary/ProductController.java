package com.example.final_project.project.product.boundary;

import com.example.final_project.project.product.repository.ProductDTO;
import com.example.final_project.project.product.repository.entity.Product;
import com.example.final_project.project.product.repository.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/product")
public class ProductController {
    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveProduct")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteProductById/{id}")
    public void deleteProductById(@PathVariable long id) {
        productService.deleteProductByID(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateProductPriceByComplexity/{id}")
    public void updateProductPriceByComplexity(@PathVariable long id) {
        productService.updateProductPriceByComplexity(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getNameAndPrice/{complexity}")
    public List<ProductDTO> testDTO(@PathVariable String complexity) {
        return productService.getNameAndPrice(complexity);
    }
}
