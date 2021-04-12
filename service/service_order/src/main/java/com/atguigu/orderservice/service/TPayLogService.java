package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-08
 */
public interface TPayLogService extends IService<TPayLog> {
    //生成付款二维码
    Map createNative(String orderNo);

    Map getPayStatus(String orderNo);

    void updateOrderStatus(Map<String,String> map);
}
