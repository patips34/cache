package com.psp.ecom.service;

import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psp.ecom.cache.CacheService;
import com.psp.ecom.dao.DaoService;
import com.psp.ecom.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private DaoService dataService;

  @Autowired
  private CacheService cacheService;

  /**
   * Get a product from cache or dataaccess
   */
  public Optional<Product> getProduct(String productKey) {

    if (StringUtils.isBlank(productKey)) {
      return Optional.empty();
    }

    Product product = (Product) cacheService.get(productKey);
    if (product == null) {
      product = (Product) dataService.get(productKey);
      cacheService.put(productKey, product);
    }
    return Optional.of(product);
  }

  /**
   * Save a product to cache and dataaccess
   */
  public Optional<Product> saveProduct(Product product) {
    if (product == null || StringUtils.isBlank(product.getKey())) {
      return Optional.empty();
    }
    dataService.put(product.getKey(), product);
    cacheService.put(product.getKey(), product);
    return Optional.of(product);
  }

}
