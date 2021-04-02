package com.atguigu.eduservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.eduservice.service.VodService;
import com.atguigu.eduservice.utils.AliyunVideoClient;
import com.atguigu.eduservice.utils.VideoPropertiesUtil;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.InputStream;

/**
 * @Author: taotao
 * @Date: 2021/3/31
 * @Version 1.0
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {

        try {
            String title = file.getName();

            String fileName = file.getName();

            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(VideoPropertiesUtil.ACCESS_KEY_ID, VideoPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);

            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;

            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVideoClient.initVodClient(VideoPropertiesUtil.ACCESS_KEY_ID,VideoPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request=new DeleteVideoRequest();
            request.setVideoIds(videoId);

            DeleteVideoResponse response=client.getAcsResponse(request);
            System.out.println("RequestId = "+response.getRequestId());

        }catch (ClientException e){
            throw new GuliException(20001,"视频删除失败");
        }
    }

    @Override
    public void deleteVideos(List<String> videoIdList) {
        try {
            DefaultAcsClient client = AliyunVideoClient.initVodClient(VideoPropertiesUtil.ACCESS_KEY_ID,VideoPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request=new DeleteVideoRequest();

            //传入多个视频ID，request支持
            String videoIds= StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoIds);

            DeleteVideoResponse response=client.getAcsResponse(request);
            System.out.println("RequestId = "+response.getRequestId());

        }catch (ClientException e){
            throw new GuliException(20001,"视频删除失败");
        }
    }
}
