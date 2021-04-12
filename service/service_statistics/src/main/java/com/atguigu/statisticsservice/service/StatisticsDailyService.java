package com.atguigu.statisticsservice.service;

import com.atguigu.statisticsservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-10
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStatistics(String day);

    Map<String, Object> getChartData(String begin, String end, String type);
}
