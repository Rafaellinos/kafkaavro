package br.com.rafaellino.kafkaavro.serdes;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;

public abstract class GenericAvroDeserializer<T> implements Deserializer<T> {
  private DatumReader<T> reader;

  private void setSchema(final Class<T> clazz) {
    Schema schema;
    try {
      Object tbd = clazz.getDeclaredConstructor().newInstance();
      if (tbd instanceof final SpecificRecordBase avroClass) {
        schema = avroClass.getSchema();
        this.reader = new SpecificDatumReader<>(schema);
      }
    } catch (Exception e) {
      schema = ReflectData.get().getSchema(clazz);
      this.reader = new ReflectDatumReader<T>(schema);
    }
  }

  public GenericAvroDeserializer(final Class<T> clazz) {
    setSchema(clazz);
  }

  @Override
  public T deserialize(String topic, byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    Decoder decoder = DecoderFactory.get().binaryDecoder(bais, null);
    try {
      return reader.read(null, decoder);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
