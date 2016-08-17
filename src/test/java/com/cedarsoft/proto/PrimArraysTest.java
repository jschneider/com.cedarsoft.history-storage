package com.cedarsoft.proto;

import com.cedarsoft.history.storage.PrimArrays;
import com.google.common.hash.HashCode;
import org.assertj.core.api.Assertions;
import org.junit.*;

import java.io.ByteArrayOutputStream;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class PrimArraysTest {
  @Test
  public void doIt() throws Exception {
    PrimArrays.ArrayHolder.Builder builder = PrimArrays.ArrayHolder.newBuilder();

    builder.addId(100);
    builder.addTime(2000);

    builder.addValueInteger(2000);
    builder.addValue64Integer(2001);
    builder.addValueDouble(2002.5);
    builder.addValueFloat(2003.5f);

    PrimArrays.ArrayHolder arrayHolder = builder.build();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    arrayHolder.writeTo(out);

    Assertions.assertThat(out.size()).isEqualTo(25);
    Assertions.assertThat(HashCode.fromBytes(out.toByteArray()).toString()).isEqualTo("086410d00f18d00f20d10f2d0070fa443100000000004a9f40");
  }
}
