package com.laze;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try(var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // hash
                jedis.hset("users:1:info", "name", "laze");

                var userInfo = new HashMap<String, String>();
                userInfo.put("email", "laze@gmail.com");
                userInfo.put("phone", "010-1234-5678");

                jedis.hset("users:1:info", userInfo);

                jedis.hgetAll("users:1:info").forEach((k, v) -> log.info("{}: {}", k, v));

                jedis.hdel("users:1:info", "phone");
                jedis.hgetAll("users:1:info").forEach((k, v) -> log.info("{}: {}", k, v));

                jedis.hincrBy("users:1:info", "visit", 1);
                log.info(jedis.hget("users:1:info", "visit"));
            }
        }
    }
}