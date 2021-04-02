package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/3/25
 * @Version 1.0
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    List<TwoSubject> children=new ArrayList<TwoSubject>();
}
