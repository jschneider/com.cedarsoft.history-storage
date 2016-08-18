package com.cedarsoft.proto;

import com.cedarsoft.history.storage.Data;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class ManualOutput {
  @Benchmark
  public void writeByteBuffer() throws Exception {
    Data data = new Data();

    ByteBuffer byteBuffer = ByteBuffer.allocate((data.getMillis().length * 8) + (data.getValues().length * 8));

    for (long value : data.getMillis()) {
      byteBuffer.putLong(value);
    }

    for (double value : data.getValues()) {
      byteBuffer.putDouble(value);
    }
  }

  @Benchmark
  public void writeByteBufferDirect() throws Exception {
    Data data = new Data();

    ByteBuffer byteBuffer = ByteBuffer.allocateDirect((data.getMillis().length * 8) + (data.getValues().length * 8));

    for (long value : data.getMillis()) {
      byteBuffer.putLong(value);
    }

    for (double value : data.getValues()) {
      byteBuffer.putDouble(value);
    }
  }
}
