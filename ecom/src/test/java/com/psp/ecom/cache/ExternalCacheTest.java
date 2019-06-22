package com.psp.ecom.cache;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import com.psp.ecom.cache.dto.CacheValue;
import com.psp.ecom.cache.entity.Data;
import com.psp.ecom.cache.repo.DataRepo;
import com.psp.ecom.config.ExternalCacheConfig;

@RunWith(SpringRunner.class)
public class ExternalCacheTest {

  @Mock
  private DataRepo dataRepo;

  @Mock
  private ExternalCacheConfig config;

  @InjectMocks
  private ExternalCache extCache;

  private void givenCachConfig(int ttl) {
    Mockito.when(config.getTtl()).thenReturn(ttl);
    extCache.onApplicationEvent(null);
  }

  private void givenDefaultConfig() {
    givenCachConfig(2000);
  }

  @Test
  public void testGet() {
    givenDefaultConfig();
    String key = "1";
    Object obj = new Object();
    Data data = new Data(key, new CacheValue(obj));
    Mockito.when(dataRepo.findByKey(key)).thenReturn(data);
    Object actual = extCache.get(key);
    assertEquals(data, actual);
  }

  @Test
  public void testPut() {
    givenDefaultConfig();
    String key = "1";
    Object obj = new Object();
    extCache.put(key, obj);
    Mockito.verify(dataRepo, Mockito.times(1)).save(Mockito.any(Data.class));
  }

  @Test
  public void testEvictCache() {
    givenCachConfig(3000);
    Data data = new Data("1", new CacheValue("laptop"));
    List<Data> dataList = new ArrayList<>();
    dataList.add(data);
    Mockito.when(dataRepo.findAll()).thenReturn(dataList);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // Verify if the clean is getting called
    Mockito.verify(dataRepo, Mockito.atLeastOnce()).deleteAll(dataList);

  }

}
