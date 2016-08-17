package com.cedarsoft.proto;

import com.cedarsoft.history.storage.Data;
import com.esotericsoftware.kryo.io.FastOutput;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.ByteArrayOutputStream;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class KyroBenchmark {
  @Benchmark
  public void writeUnsafeOutput() throws Exception {
    Data data = new Data();

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try (Output output = new UnsafeOutput(out)) {
      output.writeLongs(data.getMillis());
      output.writeDoubles(data.getValues());
    }
  }

  @Benchmark
  public void writeFastOutput() throws Exception {
    Data data = new Data();

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try (Output output = new FastOutput(out)) {
      output.writeLongs(data.getMillis());
      output.writeDoubles(data.getValues());
    }
  }

  @Benchmark
  public void writeOutput() throws Exception {
    Data data = new Data();

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try (Output output = new Output(out)) {
      output.writeLongs(data.getMillis());
      output.writeDoubles(data.getValues());
    }
  }
}
