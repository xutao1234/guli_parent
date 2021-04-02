package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/4/1
 * @Version 1.0
 */
@Component
public class VodClientFeignClient implements VodClient {
    @Override
    public R deleteAlyVideo(String videoId) {
        return R.ERROR().message("删除视频失败了");
    }

    @Override
    public R deleteAlyVideos(List<String> videoIdList) {
        return R.ERROR().message("删除多个视频失败了");
    }
}
