package com.laze;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try(JedisPool jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(var jedis = jedisPool.getResource()) {
                // sorted set
                var scores = new HashMap<String, Double>();
                scores.put("user1", 100.0);
                scores.put("user2", 200.0);
                scores.put("user3", 300.0);
                scores.put("user4", 400.0);
                scores.put("user5", 500.0);

                jedis.zadd("game2:scores", scores);

                List<String> zrange = jedis.zrange("game2:scores", 0, Long.MAX_VALUE);
                zrange.forEach(System.out::println);

                List<Tuple> zrangeWithScore = jedis.zrangeWithScores("game2:scores", 0, Long.MAX_VALUE);
                zrangeWithScore.forEach(System.out::println);

                log.info("{}", jedis.zcard("game2:scores"));

                jedis.zincrby("game2:scores", 1000, "user5");

                log.info("{}", jedis.zrevrangeWithScores("game2:scores", 0, Long.MAX_VALUE));

            }
        }
    }
}