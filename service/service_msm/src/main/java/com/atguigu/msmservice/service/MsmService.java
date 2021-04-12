package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @Author: taotao
 * @Date: 2021/4/5
 * @Version 1.0
 */
public interface MsmService {
    boolean send(String phone, String sms_180051135, Map<String, Object> param);
}
