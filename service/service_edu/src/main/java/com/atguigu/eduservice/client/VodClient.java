package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/4/1
 * @Version 1.0
 */

@FeignClient(name = "service-vod",fallback = VodClientFeignClient.class)
@Component
public interface VodClient {

    // 删除服务器中视频
    @DeleteMapping(value = "/eduvod/video/deleteAlyVideo/{videoId}")
    public R deleteAlyVideo(@PathVariable("videoId") String videoId);


    // 同时删除多个视频（阿里云服务器）
    @DeleteMapping("deleteAlyVideos")
    public R deleteAlyVideos(@RequestParam("videoIdList") List<String> videoIdList);
}
