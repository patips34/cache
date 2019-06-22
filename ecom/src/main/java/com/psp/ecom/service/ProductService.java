package com.psp.ecom.service;

import java.util.Optional;
import com.psp.ecom.model.Product;

public interface ProductService {

  public Optional<Product> getProduct(String productKey);

  public Optional<Product> saveProduct(Product product);
}
