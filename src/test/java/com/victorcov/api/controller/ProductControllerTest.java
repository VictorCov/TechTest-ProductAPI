package com.victorcov.api.controller;

import com.victorcov.api.entity.Product;
import com.victorcov.api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product("1", "Product1","Product1", 100.0);
        Product product2 = new Product("2", "Product2","Product2", 200.0);
        when(productRepository.findAll()).thenReturn(Flux.just(product1, product2));
        StepVerifier.create(productController.getAllProducts())
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void testGetProductByProductId_Found() {
        String productId = "1";
        Product product = new Product(productId, "Product1","Product1", 100.0);
        when(productRepository.findByProductId(eq(productId))).thenReturn(Flux.just(product));
        StepVerifier.create(productController.getProductsByProductId(productId))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void testGetProductByProductId_NotFound() {
        String productId = "1";
        when(productRepository.findByProductId(eq(productId))).thenReturn(Flux.empty());
        StepVerifier.create(productController.getProductsByProductId(productId))
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();
    }

    @Test
    void testGetProductsByIds_Found() {
        List<String> productIds = Arrays.asList("1", "2");
        Product product1 = new Product("1", "Product1","Product1",100.0);
        Product product2 = new Product("2", "Product2","Product2", 200.0);
        when(productRepository.findByProductIdIn(eq(productIds))).thenReturn(Flux.just(product1, product2));
        StepVerifier.create(productController.getProductsByIds("1,2"))
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void testGetProductsByIds_NotFound() {
        List<String> productIds = Arrays.asList("1", "2");
        when(productRepository.findByProductIdIn(eq(productIds))).thenReturn(Flux.empty());
        StepVerifier.create(productController.getProductsByIds("1,2"))
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();
    }
}