package com.psp.ecom.cache;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.psp.ecom.cache.dto.CacheValue;
import com.psp.ecom.cache.entity.Data;
import com.psp.ecom.cache.repo.DataRepo;
import com.psp.ecom.config.ExternalCacheConfig;

/**
 * External Cache is implemented using Mongo as backing store
 * 
 * @author prash
 *
 */
@Component
public class ExternalCache implements Cache, Runnable, ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  private ExternalCacheConfig config;

  private int ttl;

  @Autowired
  private DataRepo dataRepo;

  @Override
  public Object get(String key) {
    return dataRepo.findByKey(key);
  }

  @Override
  public void put(String key, Object value) {
    dataRepo.save(new Data(key, new CacheValue(value)));
  }

  /**
   * Initialize with configurations once application is ready
   */
  @Override
  public void onApplicationEvent(ApplicationReadyEvent arg0) {
    this.ttl = config.getTtl();
    new Thread(this).start();
  }

  /**
   * Logic to evict TTL cache
   */
  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      List<Data> dataToEvict = dataRepo.findAll().stream()
          .filter(data -> isTTLMet(data.getValue().getStartTime())).collect(Collectors.toList());

      dataRepo.deleteAll(dataToEvict);
    }
  }

  private boolean isTTLMet(long startTime) {
    return this.ttl >= System.currentTimeMillis() - startTime;
  }


}
