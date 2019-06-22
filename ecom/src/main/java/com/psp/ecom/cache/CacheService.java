package com.psp.ecom.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is cache service to be used by all other services who want to cache it or get it from the
 * cache. Consumers of this service don't need to know internal details of the cache.
 * 
 * @author prash
 *
 */
@Service
public class CacheService implements Cache {

  @Autowired
  private InternalCache internalCache;

  @Autowired
  private ExternalCache externalCache;

  @Override
  public Object get(String key) {

    Object obj = internalCache.get(key);
    if (obj == null) {
      obj = externalCache.get(key);
    }
    return obj;
  }

  @Override
  public void put(String key, Object value) {

    if (key != null) {
      internalCache.put(key, value);
      externalCache.put(key, value);
    }

  }

}
