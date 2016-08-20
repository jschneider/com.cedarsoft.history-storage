package com.cedarsoft.proto;

import com.cedarsoft.history.storage.Data;
import com.google.common.base.Stopwatch;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
@State(Scope.Thread)
public class ManualOutput {
  private final Data data = new Data();
  private final ByteBuffer directBuffer = ByteBuffer.allocateDirect(calculateCapacity(data));
  private final ByteBuffer byteBuffer = ByteBuffer.allocate(calculateCapacity(data));

  @Benchmark
  public void writeByteBuffer() throws Exception {
    byteBuffer.rewind();

    for (long value : data.getMillis()) {
      byteBuffer.putLong(value);
    }

    for (double value : data.getValues()) {
      byteBuffer.putDouble(value);
    }
  }

  @Benchmark
  public void writeByteBufferDirect() throws Exception {
    directBuffer.rewind();

    for (long value : data.getMillis()) {
      directBuffer.putLong(value);
    }

    for (double value : data.getValues()) {
      directBuffer.putDouble(value);
    }
  }

  private static int calculateCapacity(Data data) {
    return (data.getMillis().length * 8) + (data.getValues().length * 8);
  }

  public static void main(String[] args) throws Exception {
    ManualOutput manualOutput = new ManualOutput();

    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; i < 100; i++) {
      manualOutput.writeByteBufferDirect();
    }
    stopwatch.stop();

    System.out.println("10 Took: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    int capacity = calculateCapacity(new Data());
    System.out.println("Buffer Size: " + capacity + " Bytes");
    System.out.println("Buffer Size: " + capacity / 1024.0 + " KB");
    System.out.println("Buffer Size: " + capacity / 1024.0 / 1024.0 + " MB");
  }
}
