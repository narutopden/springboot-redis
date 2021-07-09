package com.javaclient.demo.Controller;

import com.javaclient.demo.model.Product;
import com.javaclient.demo.service.ProductService;
import com.javaclient.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @author Blue_Sky 7/8/21
 */
@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("save")
    public JsonData save(@RequestBody Product product){
        product.setCreateTime(new Date());
        int save = productService.save(product);
        return JsonData.buildSuccess(save);
    }

    @PostMapping("upDate")
    public JsonData update(@RequestBody Product product){
        Product product1 = productService.updateById(product);
        return JsonData.buildSuccess(product1);
    }

    @DeleteMapping("delete")
    public JsonData deleteById(int id ){
        int delete = productService.deleteById(id);
        return JsonData.buildSuccess(delete);
    }

    @GetMapping("find")
    public JsonData findById(int id){
        Product product = productService.findById(id);
        return JsonData.buildSuccess(product);
    }

    @GetMapping("page")
    public JsonData page(int page, int size){
        Map<String, Object> page1 = productService.page(page, size);
        return JsonData.buildSuccess(page1);
    }


}
