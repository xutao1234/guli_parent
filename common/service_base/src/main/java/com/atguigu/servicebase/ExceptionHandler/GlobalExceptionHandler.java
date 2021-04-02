package com.atguigu.servicebase.ExceptionHandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: taotao
 * @Date: 2021/3/16
 * @Version 1.0
 * 统一异常类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常时执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody // 为了能够返回数据，而不是返回500异常
    public R error(Exception e){
        e.printStackTrace();
        return R.ERROR().message("执行了全局异常处理类");
    }

    //特定异常ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody // 为了能够返回数据，而不是返回500异常
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.ERROR().message("执行了ArithmeticException异常处理类");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody // 为了能够返回数据，而不是返回500异常
    public R error(GuliException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return R.ERROR().code(e.getCode()).message(e.getMsg());
    }
}
