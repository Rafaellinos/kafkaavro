package br.com.rafaellino.kafkaavro.serdes;

import br.com.rafaellino.Person;
import org.apache.kafka.common.serialization.Serdes;

public class PersonSerde extends Serdes.WrapperSerde<Person> {

  private PersonSerializer serializer;
  private PersonDeserializer deserializer;

  public PersonSerde() {
    super(new PersonSerializer(), new PersonDeserializer());
  }
}
