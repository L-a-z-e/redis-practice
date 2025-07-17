package com.laze.springbootcache;

import com.laze.springbootcache.domain.entity.User;
import com.laze.springbootcache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class SpringbootcacheApplication implements ApplicationRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootcacheApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}
}
