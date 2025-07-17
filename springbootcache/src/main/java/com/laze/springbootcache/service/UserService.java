package com.laze.springbootcache.service;

import com.laze.springbootcache.domain.entity.User;
import com.laze.springbootcache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public User getUser(final Long id) {
        // 1. cache get
        var key = "users:%d".formatted(id);
        var cachedUser = objectRedisTemplate.opsForValue().get(key);

        if (cachedUser != null) {
            return (User)cachedUser;
        }

        // 2. else db -> cache set
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
        return user;
    }
}
