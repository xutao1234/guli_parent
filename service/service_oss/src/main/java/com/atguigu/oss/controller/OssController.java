package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: taotao
 * @Date: 2021/3/23
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //获取上传文件  MultipartFile
        //返回上传oss的路径
        String url=ossService.uploadFileAvatar(file);

        return R.OK().data("url",url);
    }









}
