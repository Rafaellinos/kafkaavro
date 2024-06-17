package br.com.rafaellino.kafkaavro.serdes;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;

public abstract class GenericAvroSerializer<T> implements Serializer<T> {
  /*
  Points to consider:
    This serializer is instantiated ONCE per consumer/producer. Even thought the constructor became a little bit slow
    (reflection), the process is done only in the initialization.

    Using bytearray or the encoder in the constructor could lead to bugs, considering many threads/process using
    the same stream of data.
   */
  private DatumWriter<T> writer;

  private void setSchema(final Class<T> clazz) {
    Schema schema;
    try {
      // for avro class
      Object tbd = clazz.getDeclaredConstructor().newInstance();
      if (tbd instanceof final SpecificRecordBase avroClass) {
        schema = avroClass.getSchema();
        this.writer = new SpecificDatumWriter<>(schema);
      }
    } catch (Exception e) {
      // any other class using reflection
      schema = ReflectData.get().getSchema(clazz);
      this.writer = new ReflectDatumWriter<>(schema);
    }
  }

  public GenericAvroSerializer(final Class<T> clazz) {
    setSchema(clazz);
  }

  @Override
  public byte[] serialize(String topic, T data) {
    if (data == null) {
      return null;
    }
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
      writer.write(data, encoder);
      encoder.flush();
      return outputStream.toByteArray();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
