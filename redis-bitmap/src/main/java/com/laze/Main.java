package com.laze;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.stream.IntStream;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try  (var jedis = jedisPool.getResource()) {

                jedis.setbit("request-somepage-20250714", 100, true);
                jedis.setbit("request-somepage-20250714", 200, true);
                jedis.setbit("request-somepage-20250714", 300, true);
                jedis.setbit("request-somepage-20250714", 400, true);

                log.info("{}", jedis.getbit("request-somepage-20250714", 100));
                log.info("{}", jedis.getbit("request-somepage-20250714", 50));

                log.info("{}", jedis.bitcount("request-somepage-20250714"));

                // bitmap vs set
                final Pipeline pipelined = jedis.pipelined();
                IntStream.rangeClosed(0, 1000000).forEach(i -> {
                    pipelined.sadd("request-somepage-set", String.valueOf(i), "1");
                    pipelined.setbit("request-somepage-bit", i, true);

                    if (i == 10000) {
                        pipelined.sync();
                    }
                });
                pipelined.sync();
            }
        }

    }
}