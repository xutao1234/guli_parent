package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteVideo(String courseId) {

        // 删除小节相关的视频
        // 查出所有的视频ID
        QueryWrapper<EduVideo> videoIdQueryWrapper=new QueryWrapper<>();
        videoIdQueryWrapper.eq("course_id",courseId);
        videoIdQueryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(videoIdQueryWrapper);

        List<String> videoIds=new ArrayList<>();
        for (int i=0;i<eduVideoList.size();i++){
            //如果是单个字段的Object集合使用Object判断
            Object object=eduVideoList.get(i);
            //判断一下，如果视频ID为空，就不用添加到集合中
            if(object!=null){
                videoIds.add(eduVideoList.get(i).getVideoSourceId());
            }

            //如果是两个或以上字段的对象集合，可用下方这种方式判空
            //if(!StringUtils.isEmpty(eduVideoList.get(i).getVideoSourceId())){
            //    videoIds.add(eduVideoList.get(i).getVideoSourceId());
            //}
        }
        //如果视频ID集合为空，就不必执行删除视频接口
        if(videoIds.size()>0) {
            vodClient.deleteAlyVideos(videoIds);
        }

        R r=vodClient.deleteAlyVideo("sdf");
        if(r.getCode()==20001){
            throw new GuliException(20001,"熔断器执行了");
        }

        //删除小节信息
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
