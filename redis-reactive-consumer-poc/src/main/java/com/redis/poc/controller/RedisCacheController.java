package com.redis.poc.controller;

import com.redis.poc.model.Customer;
import com.redis.poc.service.CustomerCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class RedisCacheController {

    @Autowired
    private CustomerCacheService customerCacheService;

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public Mono<Customer> put(@RequestBody Customer customer) {
        return customerCacheService.put(customer);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
    public Mono<Customer> get(@RequestParam("id") String id) {
        return customerCacheService.get(id);
    }

    @RequestMapping(value = "/getKeys", method = RequestMethod.GET)
    public Flux<String> getKeys() {
        return customerCacheService.getKeys();
    }

    @RequestMapping(value = "/getNonReactive", method = RequestMethod.GET)
    public Customer getNonReactive(@RequestParam("id") String customerId) {
        return customerCacheService.getNonReactive(customerId);
    }

    @RequestMapping(value = "/getCusKeys", method = RequestMethod.GET)
    public Mono<Set<String>> getCusKeys() {
        return customerCacheService.getCusKeys();
    }
}
