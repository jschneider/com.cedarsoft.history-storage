package com.cedarsoft.history.storage;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class Data {
  public static final int COUNT = 10000;
  public static final int DEPTH = 600;

  private final long[] millis;
  private final double[] values;

  public Data() {
     millis = new long[DEPTH];
     values = new double[DEPTH*COUNT];

     for (int i=0;i<millis.length;i++) {
       millis[i] = i * 1531;
     }
     for (int i=0;i<values.length;i++) {
       values[i] = i * 100.5;
     }
   }

  public Data(long[] millis, double[] values) {
    this.millis = millis;
    this.values = values;
  }

  public long[] getMillis() {
    return millis;
  }

  public double[] getValues() {
    return values;
  }
}
