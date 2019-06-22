package com.psp.ecom.cache.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.psp.ecom.cache.entity.Data;

/**
 * Mongodb repository interface for caching the data
 * 
 * @author prash
 *
 */
public interface DataRepo extends MongoRepository<Data, Long> {

  public Data findByKey(String key);

}
