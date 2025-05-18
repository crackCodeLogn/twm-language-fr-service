package com.vv.personal.twm.language.fr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class TwmLanguageFrServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TwmLanguageFrServiceApplication.class, args);
  }
}
