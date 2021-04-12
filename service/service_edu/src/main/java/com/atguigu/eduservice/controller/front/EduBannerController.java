package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/4/3
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class EduBannerController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    //前台首页热门名师课程数据
    @GetMapping("index")
    public R index(){
        //查询前8条课程
        QueryWrapper<EduCourse> courseWrapper=new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList=courseService.list(courseWrapper);


        //查询前4条名师
        QueryWrapper<EduTeacher> teacherWrapper=new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList=teacherService.list(teacherWrapper);

        return R.OK().data("courseList",courseList).data("teacherList",teacherList);
    }
}
