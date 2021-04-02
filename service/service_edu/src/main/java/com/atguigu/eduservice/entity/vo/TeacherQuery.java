package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: taotao
 * @Date: 2020/1/6 17:50
 * @Version 1.0
 */
@ApiModel(value = "Teacher查询对象",description = "讲师查询对象封装")
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "级别")
    private Integer level;
    @ApiModelProperty(value = "查询开始时间")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需转换
    @ApiModelProperty(value = "查询结束时间")
    private String end;
}
