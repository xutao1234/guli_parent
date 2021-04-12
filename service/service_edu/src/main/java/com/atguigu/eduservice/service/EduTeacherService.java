package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-15
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherlistPage(Page<EduTeacher> pageParam);
}
