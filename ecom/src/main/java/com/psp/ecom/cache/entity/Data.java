package com.psp.ecom.cache.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import com.psp.ecom.cache.dto.CacheValue;

/**
 * Entity used to save to mongo db for cache purpose.
 * 
 * @author prash
 *
 */
@Document(collection = "data")
public class Data {

  private String key;
  private CacheValue value;

  public Data(String key, CacheValue value) {

    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public CacheValue getValue() {
    return value;
  }

  public void setValue(CacheValue value) {
    this.value = value;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
