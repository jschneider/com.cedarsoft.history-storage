package com.cedarsoft.history.storage;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class Data {
  private final long[] millis;
  private final double[] values;

  public Data() {
     millis = new long[1000];
     values = new double[1000];

     for (int i=0;i<millis.length;i++) {
       millis[i] = i * 1000;
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
