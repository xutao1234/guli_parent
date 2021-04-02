package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoServiceImpl eduVideoServiceImpl;
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper chapterWrapper=new QueryWrapper();
        chapterWrapper.eq("course_id",courseId);
        //查询课程大纲
        List<EduChapter> eduChapterList=baseMapper.selectList(chapterWrapper);
        //查询章节小节
        QueryWrapper videoWrapper=new QueryWrapper();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList=eduVideoServiceImpl.list(videoWrapper);


        List<ChapterVo> findChapterList=new ArrayList<>();

        for (EduChapter eduChapter : eduChapterList) {
            //大纲
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            findChapterList.add(chapterVo);

            List<VideoVo> videoVos=new ArrayList<>();
            //章节
            for (EduVideo eduVideo : eduVideoList) {
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            // 把封装之后小节List集合，放到章节对象里面
            chapterVo.setChildren(videoVos);
        }


        return findChapterList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //删除之前判断章节下有没有章节小节
        QueryWrapper videoWrapper=new QueryWrapper();
        videoWrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(videoWrapper);
        if(count>0){// 查询出小节不能删除
            throw new GuliException(20001,"不能删除");
        }else{// 不能查出小节，则删除章节
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }

    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        // 删除章节信息通过课程ID
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
