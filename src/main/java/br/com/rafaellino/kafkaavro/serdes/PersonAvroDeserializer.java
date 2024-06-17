package br.com.rafaellino.kafkaavro.serdes;

import br.com.rafaellino.Person;

public class PersonAvroDeserializer extends GenericAvroDeserializer<Person> {
  public PersonAvroDeserializer() {
    super(Person.class);
  }
}
