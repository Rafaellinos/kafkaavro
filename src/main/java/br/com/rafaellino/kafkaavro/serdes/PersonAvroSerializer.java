package br.com.rafaellino.kafkaavro.serdes;

import br.com.rafaellino.Person;

public class PersonAvroSerializer extends GenericAvroSerializer<Person> {
  public PersonAvroSerializer() {
    super(Person.class);
  }
}
