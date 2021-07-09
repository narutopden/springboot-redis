package com.javaclient.demo.service;

import com.javaclient.demo.model.Product;

import java.util.Map;

/**
 * @author Blue_Sky 7/8/21
 */
public interface ProductService {
    int save(Product product);

    int deleteById(int id);

    Product updateById(Product product);

    Product findById(int id);

    Map<String, Object> page (int page,int size);
}
