package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){

        subjectService.saveSubject(file,subjectService);

        return R.OK();
    }

    //按照树形结构查询所有课程列表
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //集合泛型是一级分类
        List<OneSubject> subjects = subjectService.getAllSubject();

        return R.OK().data("list",subjects);
    }

}

