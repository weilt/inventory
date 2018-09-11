package com.weilt.productservice.service;

import com.weilt.productservice.request.Request;

/**
 * 针对productId，异步多线程执行
 * @author weilt
 * @com.weilt.eshopproduct.service
 * @date 2018/8/27 == 1:22
 */
public interface RequestAsyncProcessService {
    void process(Request request);
}
