package com.psp.ecom.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import com.psp.ecom.config.InternalCacheConfig;

@RunWith(SpringRunner.class)
public class InternalCacheTest {

  @Mock
  private InternalCacheConfig config;

  @InjectMocks
  private InternalCache internalCache;

  public void givenCachConfig(int size, int ttl) {
    Mockito.when(config.getSize()).thenReturn(size);
    Mockito.when(config.getTtl()).thenReturn(ttl);
    internalCache.onApplicationEvent(null);
  }

  public void givenDefaultCacheConfig() {
    givenCachConfig(10, 30 * 1000);
  }

  public void givenCacheSize(int size) {
    givenCachConfig(size, 30 * 1000);
  }

  public void givenCacheTtl(int ttl) {
    givenCachConfig(10, ttl);
  }

  @Test
  public void testCache_success() {

    givenDefaultCacheConfig();
    internalCache.put("1", "one");
    String result = (String) internalCache.get("1");
    assertEquals("one", result);

  }

  @Test
  public void testCache_maxSize_stopCaching() {

    givenCacheSize(2);

    internalCache.put("1", "one");
    internalCache.put("2", "two");
    internalCache.put("3", "three");

    String result = (String) internalCache.get("2");
    assertEquals("two", result);
    result = (String) internalCache.get("3");
    assertNull(result);

  }

  @Test
  public void testCache_maxTTL_evictEntry() {
    givenCacheTtl(2000);

    internalCache.put("1", "one");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    String result = (String) internalCache.get("1");
    assertNull(result);

  }



}
