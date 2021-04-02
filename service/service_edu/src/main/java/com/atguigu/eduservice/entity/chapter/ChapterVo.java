package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/3/29
 * @Version 1.0
 */
@Data
public class ChapterVo {
    private String id;

    private String title;

    private List<VideoVo> children=new ArrayList<>();
}
