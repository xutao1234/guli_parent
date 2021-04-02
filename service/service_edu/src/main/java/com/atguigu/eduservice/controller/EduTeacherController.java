package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin // 解决跨域
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有教师信息
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> users=eduTeacherService.list(null);
        return R.OK().data("items",users);
    }
    //删除教师信息
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name="id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag=eduTeacherService.removeById(id);
        if (flag){
            return R.OK();
        }else {
            return R.ERROR();
        }
    }

    //测试多线程
    //@GetMapping("testThread")
    //public List<EduTeacher> testThread(){
    //    List<EduTeacher> users=eduTeacherService.list(null);
    //    Thread thread=new Thread(){
    //      public void run(){
    //          try {
    //              Thread.sleep(10000);
    //          } catch (InterruptedException e) {
    //              e.printStackTrace();
    //          }
    //          for (EduTeacher user : users) {
    //              System.out.println(user.getName());
    //          }
    //      }
    //    };
    //    thread.start();
    //    return users;
    //}

    //分页查询教师
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable long current,
                         @PathVariable long limit){
        Page<EduTeacher> pages=new Page<>(current,limit);

        eduTeacherService.pageMaps(pages,null);

        List<EduTeacher> records=pages.getRecords();
        long total=pages.getTotal();

        return R.OK().data("total",total).data("rows",records);
    }

    //多条件分页查询教师信息
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageParam=new Page<>(current,limit);

        //构架查询条件
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        //try {
        //    int i = 10 / 0;
        //}catch (Exception e){
        //    throw  new GuliException(2001,"执行了自定义异常处理类");
        //}
        //多条件组合查询
        //mybatis中动态sql
        String name=teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end=teacherQuery.getEnd();
        //判断条件是否为空
        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.eq("gmt_modified",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        eduTeacherService.page(pageParam,wrapper);

        long total=pageParam.getTotal();
        List<EduTeacher> records=pageParam.getRecords();

        return R.OK().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "添加教师信息")
    @PostMapping("insertTeacher")
    public R insertTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);

        if(save){
            return R.OK();
        }else{
            return R.ERROR();
        }
    }

    //根据讲师ID进行查询
    @GetMapping(value = "getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher=eduTeacherService.getById(id);
        return R.OK().data("teacher",eduTeacher);
    }

    //讲师修改功能
    @PostMapping(value = "updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.OK();
        }else{
            return R.ERROR();
        }
    }
}

