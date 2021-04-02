package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    // 注入课程描述
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    // 注入小节
    @Autowired
    private EduVideoService eduVideoService;
    // 注入章节
    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {
        //添加课程信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            throw new GuliException(20001,"课程添加失败！");
        }

        //课程ID
        String cid=eduCourse.getId();

        //添加描述信息
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //查询课程信息通过courseid
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程信息
        EduCourse eduCourse=baseMapper.selectById(courseId);

        //返回课程对象
        CourseInfoVo courseInfoVo=new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询描述信息
        EduCourseDescription eduCourseDescription=eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }

        // 修改描述信息
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());

        eduCourseDescriptionService.updateById(eduCourseDescription);

    }

    @Override
    public CoursePublishVo getCoursePublish(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.getPublishCourse(courseId);
        return coursePublishVo;
    }

    @Override
    @Transactional
    public void deleteCourse(String courseId) {
        //删除课程信息

        //删除章节小节
        eduVideoService.deleteVideo(courseId);
        //删除章节
        eduChapterService.deleteChapterByCourseId(courseId);

        //删除课程描述，课程ID就是描述信息的主键
        eduCourseDescriptionService.removeById(courseId);

        //删除课程本身信息
        int delete = baseMapper.deleteById(courseId);
        if(delete == 0){
            throw new GuliException(20001,"删除课程失败");
        }
    }


}
