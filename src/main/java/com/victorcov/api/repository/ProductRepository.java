package com.victorcov.api.repository;


import com.victorcov.api.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;


@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByProductIdIn(List<String> productIds);
    Flux<Product> findByProductId(String productId);

}