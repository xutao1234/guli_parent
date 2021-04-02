package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/3/31
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    // 上传文件小节文件视频
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){

        //返回的文件路径
        String url=vodService.uploadVideoAly(file);
        return R.OK().data("videoId",url);
    }

    // 删除小节视频（阿里云服务器）
    @DeleteMapping("deleteAlyVideo/{videoId}")
    public R deleteAlyVideo(@PathVariable String videoId){
        vodService.deleteVideo(videoId);
        return R.OK().message("视频删除成功");
    }

    // 同时删除多个视频（阿里云服务器中的视频）
    @DeleteMapping("deleteAlyVideos")
    public R deleteAlyVideos(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteVideos(videoIdList);
        return R.OK();
    }
}
