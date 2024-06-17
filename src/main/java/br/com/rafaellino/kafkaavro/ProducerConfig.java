package br.com.rafaellino.kafkaavro;

import br.com.rafaellino.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class ProducerConfig {
  @Bean
  public Supplier<Person> producePerson() {
    var a = new AtomicInteger();
    return () -> {
      Person p = new Person();
      p.setAge(a.incrementAndGet());
      p.setFirstName("Ram" + a);
      p.setLastName("V" + a);
      log.info("produzindo pessoa {}", p);
      return p;
    };
  }
}
