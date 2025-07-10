package com.laze;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

@Slf4j
public class Main {
    public static void main(String[] args) {

        try (JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                jedis.set("users:300:name", "kim");
                jedis.set("users:300:email", "kim@email.com");
                jedis.set("users:300:age", "30");

                jedis.mget("users:300:name", "users:300:email", "users:300:age").forEach(log::info);

                jedis.incr("counter");
                jedis.incrBy("counter", 10);
                jedis.decr("counter");
                jedis.decrBy("counter", 5);

                Pipeline pipeline = jedis.pipelined();
                pipeline.set("users:400:name", "lee");
                pipeline.set("users:400:email", "lee@email.com");
                pipeline.set("users:400:age", "400");
                pipeline.syncAndReturnAll();

                jedis.mget("users:400:name", "users:400:email", "users:400:age").forEach(log::info);

            }
        }
    }
}