package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/eduservice/eduvideo")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    // 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.OK();
    }

    // 删除小节
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        // 删除视频
        //从中获取视频ID
        EduVideo eduVideo=eduVideoService.getById(videoId);
        // 传入视频ID
        vodClient.deleteAlyVideo(eduVideo.getVideoSourceId());

        //删除小节
        eduVideoService.removeById(videoId);
        return R.OK();
    }

    // 修改小节 TODO

}

