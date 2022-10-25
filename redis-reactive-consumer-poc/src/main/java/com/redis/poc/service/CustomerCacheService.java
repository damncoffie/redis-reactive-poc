package com.redis.poc.service;

import com.redis.poc.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface CustomerCacheService {

    public Mono<Customer> get(String id);

    public Mono<Customer> put(Customer customer);

    public Customer getNonReactive(String id);

    public Flux<String> getKeys();

    public Mono<Set<String>> getCusKeys();
}
