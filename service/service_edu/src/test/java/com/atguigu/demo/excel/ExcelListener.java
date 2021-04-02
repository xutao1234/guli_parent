package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: taotao
 * @Date: 2021/3/24
 * @Version 1.0
 */
//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //创建list集合封装最终的数据
    List<DemoData> list=new ArrayList<DemoData>();

    //一行一行去读取excel内容
    @Override
    public void invoke(DemoData user, AnalysisContext analysisContext) {
        System.out.println("**"+user);
        list.add(user);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
