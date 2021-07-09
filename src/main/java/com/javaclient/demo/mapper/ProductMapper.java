package com.javaclient.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaclient.demo.model.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Blue_Sky 7/8/21
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
