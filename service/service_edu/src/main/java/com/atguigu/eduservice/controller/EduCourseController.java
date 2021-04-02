package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-26
 */
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    // 查询课程列表
    @GetMapping
    public R getCourseList(){
        QueryWrapper queryWrapper=new QueryWrapper();
        List<EduCourse> eduCourseList=eduCourseService.list(queryWrapper);

        return R.OK().data("list",eduCourseList);
    }

    //添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加课程后的ID，给后面添加课程大纲使用
        String id = eduCourseService.saveCourse(courseInfoVo);
        return R.OK().data("courseId",id);
    }

    //查询课程信息通过courseid
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.OK().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.OK();
    }

    //课程最终发布信息查询
    @GetMapping("getCoursePublish/{courseId}")
    public R getCoursePublish(@PathVariable String courseId){
        CoursePublishVo coursePublishVo=eduCourseService.getCoursePublish(courseId);
        return R.OK().data("coursePublishVo",coursePublishVo);
    }

    //课程最终发布确认
    @PostMapping("updatePublish/{id}")
    public R updatePublish(@PathVariable String id){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        Boolean flag = eduCourseService.updateById(eduCourse);
        if(flag) {
            return R.OK();
        }else{
            return R.ERROR();
        }
    }

    // 删除课程信息
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        eduCourseService.deleteCourse(courseId);
        return R.OK();
    }
}

