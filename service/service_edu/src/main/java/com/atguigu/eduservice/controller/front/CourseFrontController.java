package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.front.CourseQueryVo;
import com.atguigu.eduservice.entity.front.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.internal.$Gson$Preconditions;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: taotao
 * @Date: 2021/4/7
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    //条件查询课程列表
    @PostMapping("getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable long page, @PathVariable long limit
                                , @RequestBody(required = false) CourseQueryVo courseQueryVo){//require=false在实体参数传空时不会报错
        Page<EduCourse> pageParam=new Page<>(page,limit);
        Map<String,Object> map=courseService.getCourseFrontList(pageParam,courseQueryVo);

        return R.OK().data(map);
    }


    //查询课程信息详情
    @GetMapping("getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request){
        //查询课程信息
        CourseWebVo courseWebVos = courseService.getCourseFrontInfo(courseId);

        //查询章节和小节信息
        List<ChapterVo> chapterList=chapterService.getChapterVideoByCourseId(courseId);

        //查询用户购买的状态
        Boolean areadyBuy = orderClient.isAreadyBuy(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.OK().data("courseWebVo",courseWebVos).data("chapterVideoList",chapterList).data("isBuy",areadyBuy);
    }

    //根据课程ID获取课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public CourseWebVoOrder getCourseInfo(@PathVariable String courseId){
        CourseWebVo courseWebVo=courseService.getCourseFrontInfo(courseId);
        CourseWebVoOrder courseWebVoOrder=new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
