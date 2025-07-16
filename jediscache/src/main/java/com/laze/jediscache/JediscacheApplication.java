package com.laze.jediscache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class JediscacheApplication implements ApplicationRunner {

    private final UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(JediscacheApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(
          User.builder()
                  .name("laze1")
                  .email("yysi87711@gmail.com")
                  .build()
        );
        userRepository.save(
          User.builder()
                  .name("laze2")
                  .email("yysi87712@gmail.com")
                  .build()
        );
        userRepository.save(
          User.builder()
                  .name("laze3")
                  .email("yysi87713@gmail.com")
                  .build()
        );
        userRepository.save(
          User.builder()
                  .name("laze4")
                  .email("yysi87714@gmail.com")
                  .build()
        );
    }
}
