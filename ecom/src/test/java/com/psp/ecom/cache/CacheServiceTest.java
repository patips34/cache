package com.psp.ecom.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CacheServiceTest {

  @Mock
  private InternalCache internalCache;

  @Mock
  private ExternalCache externalCache;

  @InjectMocks
  private CacheService cacheService;

  Object cachedObj = null;
  String key = null;

  @Before
  public void setup() {
    cachedObj = new Object();
    key = "key";
  }

  @Test
  public void testGet_intCacheHit_thenSuccess() {

    Mockito.when(internalCache.get(key)).thenReturn(cachedObj);
    Object result = cacheService.get(key);
    assertEquals(cachedObj, result);
  }

  @Test
  public void testGet_intCacheMiss_ExtCacheHit_thenSuccess() {

    Mockito.when(internalCache.get(key)).thenReturn(null);
    Mockito.when(externalCache.get(key)).thenReturn(cachedObj);
    Object result = cacheService.get(key);
    assertEquals(cachedObj, result);
  }

  @Test
  public void testGet_intCacheMiss_ExtCacheMiss_thenMiss() {

    Mockito.when(internalCache.get(key)).thenReturn(null);
    Mockito.when(externalCache.get(key)).thenReturn(null);
    Object result = cacheService.get(key);
    assertNull(result);
  }

  @Test
  public void testGet_nullKey_thenNull() {

    Object result = cacheService.get(key);
    assertNull(result);
  }

  @Test
  public void testPut_verifyMethodCalls_whenValidKey() {

    Mockito.doNothing().when(internalCache).put(key, cachedObj);
    Mockito.doNothing().when(externalCache).put(key, cachedObj);
    cacheService.put(key, cachedObj);
    Mockito.verify(internalCache, Mockito.times(1)).put(key, cachedObj);
    Mockito.verify(externalCache, Mockito.times(1)).put(key, cachedObj);
  }

  @Test
  public void testPut_verifyMethodCalls_whenNullKey() {

    cacheService.put(null, cachedObj);
    Mockito.verify(internalCache, Mockito.times(0)).put(key, cachedObj);
    Mockito.verify(externalCache, Mockito.times(0)).put(key, cachedObj);
  }



}
