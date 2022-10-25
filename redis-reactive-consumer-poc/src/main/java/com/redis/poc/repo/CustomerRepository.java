package com.redis.poc.repo;

import com.redis.poc.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

//@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {
}
