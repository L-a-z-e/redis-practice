package com.laze.springbootcache.service;

import com.laze.springbootcache.domain.entity.RedisHashUser;
import com.laze.springbootcache.domain.entity.User;
import com.laze.springbootcache.domain.repository.RedisHashUserRepository;
import com.laze.springbootcache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.laze.springbootcache.config.CacheConfig.CACHE1;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisHashUserRepository redisHashUserRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public User getUser(final Long id) {
        // 1. cache get
        var key = "users:%d".formatted(id);
        var cachedUser = objectRedisTemplate.opsForValue().get(key);

        if (cachedUser != null) {
            return (User) cachedUser;
        }

        // 2. else db -> cache set
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
        return user;
    }

    public RedisHashUser getUser2(final Long id) {

        var cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        });
        return cachedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public User getUser3(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }

}
