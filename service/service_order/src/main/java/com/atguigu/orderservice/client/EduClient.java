package com.atguigu.orderservice.client;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: taotao
 * @Date: 2021/4/8
 * @Version 1.0
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程ID获取课程信息
    @GetMapping("/eduservice/coursefront/getCourseInfo/{courseId}")
    public CourseWebVoOrder getCourseInfo(@PathVariable("courseId") String courseId);
}
