package com.weilt.commonentity.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author weilt
 * @com.weilt.common.service
 * @date 2018/8/23 == 14:58
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
