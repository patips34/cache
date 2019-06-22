package com.psp.ecom.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.psp.ecom.cache.dto.CacheValue;
import com.psp.ecom.config.InternalCacheConfig;

/**
 * ConcurrentHashMap used as a datastructure to save cache
 * 
 * @author prash
 *
 */
@Component
public class InternalCache implements Cache, Runnable, ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  private InternalCacheConfig config;

  // time to live for each cache entry
  private long ttl;
  // cache size
  private int size;

  private Map<String, CacheValue> cache;

  /**
   * Get the entry from the cache.
   */
  @Override
  public Object get(String key) {

    CacheValue cacheValue = cache.get(key);

    return cacheValue != null ? cacheValue.getValue() : null;
  }

  /**
   * Put entry into the cache if cache has available space
   */
  @Override
  public void put(String key, Object value) {
    if (cache.size() < this.size) {
      this.cache.put(key, new CacheValue(value));
    }
  }

  /**
   * Init Cache properties by reading cache configuration
   */
  @Override
  public void onApplicationEvent(ApplicationReadyEvent arg0) {

    // init by configs
    this.size = config.getSize();
    this.ttl = config.getTtl() * 60 * 1000;

    // init cache
    this.cache = new ConcurrentHashMap<>(this.size);

    // start cleanup task
    (new Thread(this)).start();
  }

  // This is cache cleanup task
  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      for (Map.Entry<String, CacheValue> entry : cache.entrySet()) {
        if (this.ttl >= System.currentTimeMillis() - entry.getValue().getStartTime()) {
          cache.remove(entry.getKey());
        }
      }
    }
  }

}
