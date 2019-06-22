package com.psp.ecom.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Simulate actual db access pattern
 * 
 * @author prash
 *
 */
@Service
public class DaoServiceImpl implements DaoService {


  // Mock datastructure to simulate data being saved to db
  private Map<String, Object> map = new HashMap<>();

  // Mocked implementation
  /**
   * Access the database and return the result
   */
  @Override
  public Object get(String key) {
    return map.get(key);
  }

  // Mocked implementation
  /**
   * Update to the database
   */
  @Override
  public void put(String key, Object value) {
    map.put(key, value);
  }

}
