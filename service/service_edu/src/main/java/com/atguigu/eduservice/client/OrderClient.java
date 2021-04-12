package com.atguigu.eduservice.client;

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
@FeignClient("service-order")
public interface OrderClient {

    @GetMapping("/orderservice/torder/isAreadyBuy/{courseId}/{memberId}")
    public Boolean isAreadyBuy(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
