package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: taotao
 * @Date: 2021/4/7
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //查询讲师列表（分页）
    @GetMapping("getTeacherlistPage/{page}/{limit}")
    public R getTeacherlistPage(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageParam=new Page<EduTeacher>(page,limit);
        Map<String,Object> map=teacherService.getTeacherlistPage(pageParam);
        return R.OK().data(map);
    }

    //查询讲师详情和讲师相关课程
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //讲师信息
        QueryWrapper<EduTeacher> teacherQueryWrapper=new QueryWrapper<>();
        teacherQueryWrapper.eq("id",teacherId);
        EduTeacher eduTeacher=teacherService.getOne(teacherQueryWrapper);

        //课程信息
        QueryWrapper<EduCourse> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList=courseService.list(courseQueryWrapper);
        return R.OK().data("teacherInfo",eduTeacher).data("courseList",courseList);
    }

}
