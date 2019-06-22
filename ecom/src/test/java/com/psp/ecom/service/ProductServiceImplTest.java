package com.psp.ecom.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import com.psp.ecom.cache.CacheService;
import com.psp.ecom.dao.DaoService;
import com.psp.ecom.model.Product;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private DaoService dataService;

  @Mock
  private CacheService cacheService;

  String key = "key";
  Product product = new Product(key, "laptop");

  @Test
  public void testGetProduct_verifyCacheHit() {

    Mockito.when(cacheService.get(key)).thenReturn(product);
    Optional<Product> actual = productService.getProduct(key);

    // verify result
    assertEquals(product, actual.get());

    // verify dataService.get is not getting called.
    Mockito.verify(dataService, Mockito.times(0)).get(key);

  }

  @Test
  public void testGetProduct_verifyCacheMiss() {

    Mockito.when(cacheService.get(key)).thenReturn(null);
    Mockito.when(dataService.get(key)).thenReturn(product);
    Optional<Product> actual = productService.getProduct(key);

    // verify result
    assertEquals(product, actual.get());

    // verify dataService.get is not getting called.
    Mockito.verify(dataService, Mockito.times(1)).get(key);
  }

  @Test
  public void testSave_nullProduct_dontCreate() {

    Optional<Product> actual = productService.saveProduct(null);
    assertFalse(actual.isPresent());

  }

  @Test
  public void testSave_emptyKey_dontCreate() {
    Optional<Product> actual = productService.saveProduct(new Product("", "Laptop"));
    assertFalse(actual.isPresent());
  }

  @Test
  public void testSave_validProduct_create() {
    Optional<Product> actual = productService.saveProduct(product);
    assertTrue(actual.isPresent());
    assertEquals("laptop", actual.get().getName());
    Mockito.verify(dataService, Mockito.times(1)).put(product.getKey(), product);
    Mockito.verify(cacheService, Mockito.times(1)).put(product.getKey(), product);
  }



}
