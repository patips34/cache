package com.psp.ecom.cache.dto;

public class CacheValue {
  private long startTime;
  private Object value;

  public CacheValue(Object value) {
    startTime = System.currentTimeMillis();
    this.value = value;
  }

  public long getStartTime() {
    return startTime;
  }

  public Object getValue() {
    return value;
  }
}
