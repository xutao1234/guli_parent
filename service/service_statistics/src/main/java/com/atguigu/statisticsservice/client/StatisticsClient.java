package com.atguigu.statisticsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: taotao
 * @Date: 2021/4/10
 * @Version 1.0
 */
@Component
@FeignClient("service-ucenter")
public interface StatisticsClient {

    @GetMapping("/ucenterservice/ucentermember/countRegisterNum/{day}")
    public Integer countRegisterNum(@PathVariable("day") String day);
}
