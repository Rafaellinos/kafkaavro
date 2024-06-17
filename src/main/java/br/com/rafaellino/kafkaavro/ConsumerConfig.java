package br.com.rafaellino.kafkaavro;

import br.com.rafaellino.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class ConsumerConfig {

  @Bean
  public Consumer<Person> consumePerson() {
    return (p) -> {
      log.info("consumindo pessoa {}", p);
      System.out.println(p);
    };
  }

}
