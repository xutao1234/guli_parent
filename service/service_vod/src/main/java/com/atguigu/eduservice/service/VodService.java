package com.atguigu.eduservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/3/31
 * @Version 1.0
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void deleteVideo(String videoId);

    void deleteVideos(List<String> videoIdList);
}
