package com.cedarsoft.proto;

import com.example.tutorial.Storage;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class StorageTest {
  @Test
  public void basics() throws Exception {
    ByteArrayOutputStream raw = new ByteArrayOutputStream();
    DataOutputStream rawOut = new DataOutputStream(raw);

    ByteArrayOutputStream rawWithoutIds = new ByteArrayOutputStream();
    DataOutputStream rawWithoutIdsOut = new DataOutputStream(rawWithoutIds);

    String uuidString = UUID.randomUUID().toString();
    assertThat(uuidString.getBytes().length).isEqualTo(36);

    long millis = System.currentTimeMillis();
    Storage.Entry.Builder entryBuilder = Storage.Entry.newBuilder()
      .setTime(millis)
      .setUuid(uuidString);

    rawOut.writeLong(millis);
    rawOut.write(uuidString.getBytes());
    rawWithoutIdsOut.writeLong(millis);
    rawWithoutIdsOut.write(uuidString.getBytes());


    for (int i = 0; i < 1000; i++) {
      Storage.Int32Values.Builder valuesBuilder = entryBuilder.addInt32ValuesBuilder()
        .setId(i);

      rawOut.writeInt(i);

      for (int x = 0; x < 500; x++) {
        int value = (int) Math.random();

        valuesBuilder.addValues(value);
        rawOut.writeInt(value);
        rawWithoutIdsOut.writeInt(value);
      }
    }

    //Doubles
    for (int i = 0; i < 1000; i++) {
      Storage.FloatValues.Builder valuesBuilder = entryBuilder.addFloatValuesBuilder()
        .setId(i);

      rawOut.writeInt(i);

      for (int x = 0; x < 500; x++) {
        float value = (float) Math.random();

        valuesBuilder.addValues(value);
        rawOut.writeDouble(value);
        rawWithoutIdsOut.writeDouble(value);
      }
    }

    //done
    rawOut.close();
    rawWithoutIdsOut.close();

    Storage.Entry entry = entryBuilder.build();
    int protoBufLength = entry.toByteArray().length;


    byte[] rawBytes = raw.toByteArray();
    byte[] rawWithoutIdsBytes = rawWithoutIds.toByteArray();


    NumberFormat numberFormat = NumberFormat.getIntegerInstance();
    numberFormat.setGroupingUsed(true);
    numberFormat.setMinimumIntegerDigits(8);

    System.out.println("Proto Size     " + numberFormat.format(protoBufLength));
    System.out.println("Raw Size       " + numberFormat.format(rawBytes.length));
    System.out.println("Raw Size -IDs  " + numberFormat.format(rawWithoutIdsBytes.length));

    System.out.println("---------------------------------");
    numberFormat.setMinimumIntegerDigits(5);

    System.out.println("Zipped Proto Buffer: " + numberFormat.format(zip(entry.toByteArray()).length));
    System.out.println("Zipped Raw:          " + numberFormat.format(zip(rawBytes).length));
    System.out.println("Zipped Raw -IDs:     " + numberFormat.format(zip(rawWithoutIdsBytes).length));

    System.out.println();

    System.out.println("XZ Proto Buffer:     " + numberFormat.format(xz(entry.toByteArray()).length));
    System.out.println("XZ Raw:              " + numberFormat.format(xz(rawBytes).length));
    System.out.println("XZ Raw -IDs:         " + numberFormat.format(xz(rawWithoutIdsBytes).length));
  }

  private byte[] zip(byte[] bytesToCompress) throws IOException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteOut);
    gzipOutputStream.write(bytesToCompress);
    gzipOutputStream.finish();
    gzipOutputStream.close();
    return byteOut.toByteArray();
  }

  private byte[] xz(byte[] bytesToCompress) throws IOException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

    XZCompressorOutputStream compressedOut = new XZCompressorOutputStream(byteOut, 2);
    compressedOut.write(bytesToCompress);
    compressedOut.finish();
    compressedOut.close();

    return byteOut.toByteArray();
  }
}
