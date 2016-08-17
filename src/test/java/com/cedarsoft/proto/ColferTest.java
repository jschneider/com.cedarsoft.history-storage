package com.cedarsoft.proto;

import asdf.PrimArraysColfer;
import com.google.common.hash.HashCode;
import org.assertj.core.api.Assertions;
import org.junit.*;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class ColferTest {
  @Test
  public void asdf() throws Exception {
    PrimArraysColfer colfer = new PrimArraysColfer();
    colfer.setId(new long[]{1, 2, 3, 4});

    byte[] out = new byte[1000];

    int writtenSize = colfer.marshal(out, 0);
    Assertions.assertThat(writtenSize).isEqualTo(123);

    Assertions.assertThat(HashCode.fromBytes(out).toString()).isEqualTo("086410d00f18d00f20d10f2d0070fa443100000000004a9f40");

  }
}
