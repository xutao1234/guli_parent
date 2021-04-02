package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: taotao
 * @Date: 2021/3/24
 * @Version 1.0
 */
@Data
public class DemoData {
    //设置表头名称  index设置列对应的属性
    @ExcelProperty(value = "学生编号",index = 0)
    private int sno;

    //设置表头名称
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
}
