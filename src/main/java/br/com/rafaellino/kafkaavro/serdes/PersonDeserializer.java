package br.com.rafaellino.kafkaavro.serdes;

import br.com.rafaellino.Person;

public class PersonDeserializer extends AvroDeserializer<Person> {
  public PersonDeserializer() {
    super(Person.class);
  }
}
