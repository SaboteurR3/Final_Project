package com.example.final_project.project.product.boundary;

import com.example.final_project.project.product.repository.ProductRepository;
import com.example.final_project.project.product.repository.entity.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private ProductRepository productRepository;

    public Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProduct_name("dummyProductName");
        product.setComplexity("2.7");
        product.setPrice(BigDecimal.valueOf(170));
        return product;
    }

    public List<Product> createProductList() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProduct_name("dummyProductName");
        product1.setComplexity("2.7");
        product1.setPrice(BigDecimal.valueOf(170));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProduct_name("dummyProductName2");
        product2.setComplexity("1.5");
        product2.setPrice(BigDecimal.valueOf(170));

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        return products;
    }
    @Test
    void product_whenGetAllProduct_thenSuccess() throws Exception {
        Mockito.when(productRepository.findAll()).thenReturn(createProductList());
        mockMvc.perform(get("/product/getAllProduct"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void validProductId_whenGetAllProductById_thenSuccess() throws Exception {
        Mockito.when(productRepository.findProductById(Mockito.anyLong()))
                        .thenReturn(Optional.ofNullable(createProduct()));
        mockMvc.perform(get("/product/getProductById/" + createProduct().getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void validProductId_whenDeleteProductById_thenSuccess() throws Exception {
        Mockito.doNothing().when(productRepository).deleteById(Mockito.any());
        mockMvc.perform(delete("/product/deleteProductById/" + createProduct().getId()))
                .andExpect(status().isOk());
    }
}