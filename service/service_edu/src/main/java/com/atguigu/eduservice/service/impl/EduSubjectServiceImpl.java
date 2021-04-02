package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.list.TransformedList;
import org.apache.ibatis.annotations.One;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try {
            //读取文件流
            InputStream in=file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){

        }
    }

    //查询所有课程列表
    @Override
    public List<OneSubject> getAllSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper=new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects=baseMapper.selectList(oneWrapper);

        //查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper=new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjects=baseMapper.selectList(twoWrapper);

        //最终返回结果
        List<OneSubject> finlSubjectList=new ArrayList<>();

        //将一级目录归位
        for (EduSubject oneSubject : oneSubjects) {
            //一级目录
            OneSubject oneEduSubject=new OneSubject();
            BeanUtils.copyProperties(oneSubject,oneEduSubject);
            finlSubjectList.add(oneEduSubject);

            //将二级目录归位
            List<TwoSubject> twoSubjectList=new ArrayList<>();

            for (EduSubject twoSubject : twoSubjects) {
                if(twoSubject.getParentId().equals(oneSubject.getId())){
                    //二级目录
                    TwoSubject twoEduSubject=new TwoSubject();
                    BeanUtils.copyProperties(twoSubject,twoEduSubject);
                    twoSubjectList.add(twoEduSubject);
                }
            }
            //将二级目录存入对应的一级目录下
            oneEduSubject.setChildren(twoSubjectList);
        }
        //返回课程分类列表（树形结构）
        return finlSubjectList;
    }
}
