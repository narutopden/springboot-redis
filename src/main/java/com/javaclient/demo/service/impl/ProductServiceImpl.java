package com.javaclient.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaclient.demo.mapper.ProductMapper;
import com.javaclient.demo.model.Product;
import com.javaclient.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Blue_Sky 7/8/21
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public int save(Product product) {
        return productMapper.insert(product);
    }

    @Override
    @CacheEvict(value = {"product"}, key = "#root.args[0]")
    public int deleteById(int id) {
        return productMapper.deleteById(id);
    }

    /**
     * 1. to store back the updated in the cache we need to return what got in rather an retuning a Integer
     * 2. since we need our updated data in cache need to view by findById method so we need to make sure
     *    no problems in finding the respective cache by [ SAME NAME ]
     *
     * @param product
     * @return
     */
    @Override
    @CachePut(value ={"product"}, key = "#product.id", cacheManager = "cacheManager1Minute")
    public Product updateById(Product product) {
        int flag = productMapper.updateById(product);
        return product;
    }

    /** #root.args[0] means its will differencate on user id so we need to add args[0]
     * 0= start from zero, not 0 value
     *  #root.methodName will return the method name in the cache
     *  ================================================
     *  to simplify the naming convention we can use Key_Generator to generate key for us
     *  => @Cacheable(value ={"product"}, keyGenerator = "springCacheKeyGenerator", cacheManager = "cacheManager1Minute")
     *  ================================================
     *  in some case we need use multi catch and in that scenario we can use @Caching
     * @param id
     * @return
     */
    @Override
    @Cacheable(value ={"product"}, key = "#root.args[0]", cacheManager = "cacheManager1Minute")
    //@Cacheable(value ={"product"}, keyGenerator = "springCacheKeyGenerator", cacheManager = "cacheManager1Minute")
//    @Caching(
//            cacheable = {
//                    @Cacheable(value = {"product"}, key ="#id", unless="#result == null"),
//                    @Cacheable(value = {"product"}, key="'front_'+#root.args[0]", unless="#result == null")
//            },
//            put = {
//                    @CachePut(value = {"product_test"}, key = "'update_'+#id" ,unless="#result == null")
//            }
//    )
    public Product findById(int id) {
        return productMapper.selectById(id);
    }

    /**
     * you can write key in this format [ key = "#root.methodName+'_'+#root.args[0]+'_'+#root.args[1]" ]
     * or you can write [ key = "#root.methodName+'_'+#page+'_'+#size" ]
     * #root.args[0] means first arg in the parameter and #root.args[1] means second
     * in this section #root.args[0] = page  and #root.args[1] = size
     * @param page
     * @param size
     * @return
     */
    @Override
    //@Cacheable(value = {"product_page"}, key = "#root.methodName+'_'+#page+'_'+#size")
    @Cacheable(value ={"product_page"}, keyGenerator = "springCacheKeyGenerator")
    public Map<String, Object> page(int page, int size) {
        Page pageInfo = new Page(page,size);
        IPage<Product> iPage = productMapper.selectPage(pageInfo, null);

        /* initial capacity of pageMap is set with 3 but default is 16
        and this can be change with respect to amount of size in page
         */
        Map<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record",iPage.getTotal());
        pageMap.put("total_page",iPage.getPages());
        pageMap.put("current_data",iPage.getRecords());


        return pageMap;
    }
}
