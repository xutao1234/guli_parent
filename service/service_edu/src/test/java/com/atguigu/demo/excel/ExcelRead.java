package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

/**
 * @Author: taotao
 * @Date: 2021/3/24
 * @Version 1.0
 */
public class ExcelRead {
    public static void main(String[] args) {
        String fileName="E:\\11.xlsx";

        //这里需要指定读用哪个class去读，然后读取第一个sheet，文件流会自动关闭
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
