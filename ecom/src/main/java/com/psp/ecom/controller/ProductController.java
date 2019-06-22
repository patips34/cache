package com.psp.ecom.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.psp.ecom.model.Product;
import com.psp.ecom.service.ProductService;

/**
 * Rest controller to expose services through http
 * 
 * @author prash
 *
 */
@RestController
@RequestMapping(value = "/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  // TBD
  @RequestMapping(path = "{key}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String getProduct(@PathVariable("key") String key) {
    Optional<Product> product = productService.getProduct(key);
    if (product.isPresent()) {
      // return valid response
    } else {
      // return valid error response
    }
    return "Implementation in progress...";
  }

  // TBD
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public String addPost(@RequestBody Product product) {
    // Validation
    // if failed return error message
    // else return product
    return "Implementation in progress...";
  }

}
