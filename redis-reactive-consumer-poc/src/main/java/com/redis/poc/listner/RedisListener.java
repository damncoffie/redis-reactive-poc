package com.redis.poc.listner;

import com.redis.poc.model.Customer;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class RedisListener implements ApplicationRunner {

    @Autowired
    RedissonReactiveClient redissonReactiveClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redissonReactiveClient.getTopic("customer_topic")
                .addListener(Customer.class, (charSequence, customer) -> System.out.println("Object updated!"))
                .subscribe();
    }
}
