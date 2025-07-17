package com.laze.springbootcache.domain.repository;

import com.laze.springbootcache.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
