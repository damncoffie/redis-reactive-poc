package com.redis.poc.service;

import com.redis.poc.model.Customer;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class CustomerCacheServiceImpl implements CustomerCacheService {

    private static final String CUSTOMER_MAP_NAME = "myMap";

    @Autowired
    private RedissonReactiveClient redissonReactiveClient;
    /*@Autowired
    private CustomerRepository customerRepository;*/
    private RMapReactive<String, Customer> customersCacheMap;

    @PostConstruct
    public void setCustomerMap() {
        customersCacheMap = redissonReactiveClient.getMap(CUSTOMER_MAP_NAME);
    }

    public Mono<Customer> get(String id) {
        return customersCacheMap.get(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Mono<Customer> put(Customer customer) {
        return customersCacheMap.put(customer.getId(), customer);
    }

    public Flux<String> getKeys() {
        return redissonReactiveClient.getKeys().getKeys();
    }

    public Mono<Set<String>> getCusKeys() {
        return customersCacheMap.readAllKeySet();
    }

    @Cacheable(value = CUSTOMER_MAP_NAME, key = "#customerId")
    public Customer getNonReactive(String customerId) {
        System.out.println("Inside method");
        return new Customer(customerId, "John");
    }
}
