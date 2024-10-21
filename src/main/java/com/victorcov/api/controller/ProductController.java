package com.victorcov.api.controller;

import com.victorcov.api.entity.Product;
import com.victorcov.api.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 1. Get all products
    @Operation(summary = "Get all products")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. Get a product by ID
    @Operation(summary = "Get a product by ProductId")
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getProductsByProductId(@PathVariable String productId) {
        return productRepository.findByProductId(productId).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productId)));
    }

    // 3. Get products by multiple IDs
    @Operation(summary = "Get products by multiple IDs")
    @GetMapping(value = "/by-ids", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Product> getProductsByIds(@RequestParam String ids) {
        List<String> productIds = Arrays.asList(ids.split(","));
        System.out.println("Inside getProductsByIds");
        return productRepository.findByProductIdIn(productIds).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productIds)));
    }
}