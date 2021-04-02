package com.atguigu.servicebase.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: taotao
 * @Date: 2021/3/16
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException  extends RuntimeException{
    private Integer code;
    private String msg;
}
