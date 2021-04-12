package com.atguigu.orderservice.client;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: taotao
 * @Date: 2021/4/8
 * @Version 1.0
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据用户ID获取用户信息(远程调用)
    @GetMapping("/ucenterservice/ucentermember/getUCenterInfo/{id}")
    public UcenterMemberOrder getUCenterInfo(@PathVariable("id") String id);

}
