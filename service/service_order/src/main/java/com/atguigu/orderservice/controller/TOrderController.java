package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.entity.TOrder;
import com.atguigu.orderservice.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/orderservice/torder")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    //根据课程ID生成订单，返回订单ID
    @GetMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String orderNo=orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.OK().data("orderNo",orderNo);
    }

    //根据订单编号查询订单详情
    @GetMapping("getOrderInfoByNo/{orderId}")
    public R getOrderInfoByNo(@PathVariable String orderId){
        QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);

        return R.OK().data("orderInfo",order);
    }

    //根据课程ID和用户ID查询课程是否购买
    @GetMapping("isAreadyBuy/{courseId}/{memberId}")
    public Boolean isAreadyBuy(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);

        int count = orderService.count(wrapper);
        //如果大于0，返回true，否则返回false
        return count>0;
    }

}

