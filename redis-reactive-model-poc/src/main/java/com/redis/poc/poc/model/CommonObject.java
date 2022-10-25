package com.redis.poc.poc.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonObject implements Serializable {
    private String name;
}
