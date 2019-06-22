package com.psp.ecom.dao;

/**
 * This is interface for DAO (Input to the problem statement) Implementaton is done assuming this is
 * abstraction to the database access. It can also be abstraction for accessing data from external
 * services. Here Database access scenario is considered.
 * 
 * @author prash
 *
 */
public interface DaoService {

  Object get(String key);

  void put(String key, Object value);
}
