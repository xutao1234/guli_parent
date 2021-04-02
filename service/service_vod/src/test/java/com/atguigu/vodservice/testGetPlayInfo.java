package com.atguigu.vodservice;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.io.File;
import java.util.List;
/**
 * @Author: taotao
 * @Date: 2021/3/31
 * @Version 1.0
 */
// 获取视频播放地址
public class testGetPlayInfo {
    public static void main(String[] args) throws ClientException {

        File file=new File("");
        String title="测试上传视频到aliyun视频点播";
        String fileName=file.getName();

        // 自己的秘钥，（accessKeyId,accessKeySecret）
        UploadVideoRequest request = new UploadVideoRequest("", "", title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    // 获取视频播放凭证
    public void getPlayAuth() throws ClientException{
        // 获取视频播放凭证
        // 初始化客户端、请求对象和相应对象
        DefaultAcsClient client=InitObject.initVodClient("","");
        GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        try {
            // 设置请求参数
            request.setVideoId("视频ID");
            // 获取请求相应
            response=client.getAcsResponse(request);

            //输出请求结果
            // 播放凭证
            System.out.println("PlayAuth = "+response.getPlayAuth());

            // VideoMeta信息
            System.out.println("VideoMeta.Title = "+response.getVideoMeta().getTitle());
        }catch (Exception e){
            System.out.println("ErrorMessage = "+e.getLocalizedMessage());
        }

        System.out.println("RequestId = "+response.getRequestId());
    }

    // 获取视频播放地址
    public void getPlayUrl() throws ClientException{
        // 初始化客户端请求对象和相应对象 (你的accessKeyId，你的accessKeySecret)
        DefaultAcsClient client = InitObject.initVodClient("","");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            // 设置请求参数
            // 注意：这里只能获取非加密视频的播放地址
            request.setVideoId("视频ID");
            // 获取请求相应
            response = client.getAcsResponse(request);

            // 输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            // 播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.println("playinfo.playURL = " + playInfo.getPlayURL());
            }

            //base信息
            System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle());
        }catch (Exception e){
            System.out.println("ErrorMessage = "+e.getLocalizedMessage());
        }

        System.out.println("RequestId = "+ response.getRequestId());
    }
}
