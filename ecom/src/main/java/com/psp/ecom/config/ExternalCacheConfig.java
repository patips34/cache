package com.psp.ecom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This reads the configurations from property file and provides to the External Cache service.
 * 
 * @author prash
 *
 */
@Configuration
@ConfigurationProperties(prefix = "cache.external")
public class ExternalCacheConfig {

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
