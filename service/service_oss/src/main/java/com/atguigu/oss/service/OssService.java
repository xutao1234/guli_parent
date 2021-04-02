package com.atguigu.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: taotao
 * @Date: 2021/3/23
 * @Version 1.0
 */
public interface OssService {

    String uploadFileAvatar(MultipartFile file);
}
