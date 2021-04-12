package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.orderservice.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/orderservice/tpaylog")
@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    //生成付款二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){

        Map map=payLogService.createNative(orderNo);

        return R.OK().data(map);
    }

    //查询订单状态
    @GetMapping("getPayStatus/{orderNo}")
    public R getPayStatus(@PathVariable String orderNo) {
        Map<String,String> map=payLogService.getPayStatus(orderNo);
        if (map == null) {//出错
            return R.ERROR().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return R.OK().message("支付成功");
        }

        return R.OK().code(25000).message("支付中");
    }

}

