package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    // 查询所有章节
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideoByCourseId(@PathVariable String courseId){
        List<ChapterVo> list=eduChapterService.getChapterVideoByCourseId(courseId);
        return R.OK().data("list",list);
    }


    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.OK();
    }

    //查询章节
    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter eduChapter=eduChapterService.getById(chapterId);
        return R.OK().data("chapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.OK();
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag) {
            return R.OK();
        }else{
            return R.ERROR();
        }
    }

}

