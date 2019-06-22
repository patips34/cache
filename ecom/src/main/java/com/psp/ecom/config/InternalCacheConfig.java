package com.psp.ecom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This reads the configurations from property file and provides to the Internal Cache service.
 * 
 * @author prash
 *
 */
@ConfigurationProperties(prefix = "cache.internal")
public class InternalCacheConfig {

  private int size;
  private int ttl;

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getTtl() {
    return ttl;
  }

  public void setTtl(int ttl) {
    this.ttl = ttl;
  }


}
