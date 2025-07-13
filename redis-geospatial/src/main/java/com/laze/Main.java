package com.laze;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (var jedis = jedisPool.getResource()) {
                // geo add
                jedis.geoadd("stores:geo", 126.977882, 37.566345, "서울시청");
                jedis.geoadd("stores:geo", 126.5838, 37.3443, "경복궁");

                // geo dist
                log.info("{}", jedis.geodist("stores:geo", "서울시청", "경복궁"));

                // geo search
                List<GeoRadiusResponse> radiusResponses = jedis.geosearch("stores:geo",
                        new GeoCoordinate(127.031, 37.495),
                        40000,
                        GeoUnit.M
                );

                // GeoSearchParam() => withCoord true 시 사용 가능
//                radiusResponses.forEach(response -> System.out.println("%s %f %f",
//                        response.getMemberByString(),
//                        response.getCoordinate().getLongitude(),
//                        response.getCoordinate().getLatitude())
//                );
                jedis.unlink("stores:geo");
            }
        }

    }
}