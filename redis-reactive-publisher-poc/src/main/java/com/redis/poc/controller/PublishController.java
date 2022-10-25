package com.redis.poc.controller;

import com.redis.poc.model.Customer;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

@RestController
@RequestMapping("/api")
public class PublishController {

    @Autowired
    RedissonReactiveClient redissonReactiveClient;

    @RequestMapping(value = "/publish", method = RequestMethod.PUT)
    public void publish(@RequestBody Customer customer) {
        System.out.println("About to publish");
        RTopicReactive topic = redissonReactiveClient.getTopic("customer_topic");
        topic.publish(customer)
                .doOnNext(System.out::println)
                .subscribe();
    }
}
