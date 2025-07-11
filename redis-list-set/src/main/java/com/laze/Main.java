package com.laze;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try(var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // 1. stack
                jedis.rpush("stack1", "aaaa");
                jedis.rpush("stack1", "bbbb");
                jedis.rpush("stack1", "cccc");

                List<String> stack1 = jedis.lrange("stack1", 0, -1);
                stack1.forEach(System.out::println);

                jedis.rpop("stack1");
                jedis.rpop("stack1");
                jedis.rpop("stack1");

                // 2. queue
                jedis.rpush("queue1", "dddd");
                jedis.rpush("queue1", "eeee");
                jedis.rpush("queue1", "ffff");

                log.info(jedis.lpop("queue1"));
                jedis.lpop("queue1");
                jedis.lpop("queue1");

                // 3. block, brpop, blpop
//                while(true) {
                    List<String> blpop = jedis.blpop(10,"queue:blocking");
                    if (blpop != null) {
                        blpop.forEach(System.out::println);
                    }
//                }

                // 4. set
                jedis.sadd("users:1:follow", "100", "200", "300");
                jedis.srem("users:1:follow", "100");

                jedis.smembers("users:1:follow").forEach(System.out::println);
                // log.info는 String만 처리가능 => sismember는 boolean 반환함
                log.info(String.valueOf(jedis.sismember("users:1:follow", "200")));
                // System.out.println은 내부적으로 String.valueOf 호출됨
                System.out.println(jedis.sismember("users:1:follow", "100"));
                // placeholder 사용하면 타입 자동변환 해줌
                log.info("{}", jedis.sismember("users:1:follow", "300"));

                jedis.scard("users:1:follow");
                jedis.sadd("users:2:follow", "101", "200", "300");
                log.info("{}", jedis.sinter("users:1:follow", "users:2:follow"));
                log.info("{}",jedis.smembers("users:1:follow"));
                log.info("{}",jedis.smembers("users:1:follow"));

            }

        }
    }
}