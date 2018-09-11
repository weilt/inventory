package com.weilt.productservice.service;

import com.weilt.commonentity.commonentity.ServerResponse;
import com.weilt.commonentity.entity.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author weilt
 * @com.weilt.eshopproduct.service
 * @date 2018/8/25 == 1:22
 */
@FeignClient(value = "eshop-categoryservice",fallback = Error.class)
public interface ICategorySerice {
    //需要一个服务，输入catoryId，返回一个catagory对像
    @RequestMapping(value = "/catagory/getcategory")
    ServerResponse<Category> getCateory(Integer cateoryId);

    //返回当前商品类目节点的所有子节点
    @RequestMapping(value = "/catagory/getdeepcategory")
    ServerResponse<List<Integer>> getCategoryAndDeepChildCategory(Integer categoryId);
}
