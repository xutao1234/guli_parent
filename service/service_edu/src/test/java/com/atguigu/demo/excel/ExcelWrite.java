package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: taotao
 * @Date: 2021/3/24
 * @Version 1.0
 */
public class ExcelWrite {
    public static void main(String[] args) {
        //写法1
        String fileName="E:\\11.xlsx";
        //这里需要指定写用哪个Class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        //如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName,DemoData.class).sheet("写入方法一").doWrite(data());
    }

    private static List<DemoData> data() {
        List<DemoData> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData data=new DemoData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}
