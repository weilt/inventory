package com.weilt.productservice.service;

import com.github.pagehelper.PageInfo;
import com.weilt.commonentity.commonentity.ServerResponse;
import com.weilt.commonentity.entity.Product;
import com.weilt.commonentity.entity.ProductDetailVo;

/**
 * @author weilt
 * @com.weilt.eshopproduct.service
 * @date 2018/8/23 == 12:25
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);
    ServerResponse setProductStatus(Integer productId, Integer status);
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
    ServerResponse<PageInfo> secrchProduct(String productName, Integer productId, int pageNum, int pageSize);
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
