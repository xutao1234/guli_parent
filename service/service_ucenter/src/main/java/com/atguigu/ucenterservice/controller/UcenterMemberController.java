package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/ucenterservice/ucentermember")
@CrossOrigin //跨域
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    // 会员登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember){
        String token = ucenterMemberService.login(ucenterMember);

        return R.OK().data("token",token);
    }

    //会员注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.OK();
    }

    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        String memberid= JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member=ucenterMemberService.getById(memberid);
        return R.OK().data("userInfo",member);
    }

    //根据用户ID获取用户信息(远程调用)
    @GetMapping("getUCenterInfo/{id}")
    public UcenterMemberOrder getUCenterInfo(@PathVariable String id){
        UcenterMember member=ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //根据注册时间统计注册人数
    @GetMapping("countRegisterNum/{day}")
    public Integer countRegisterNum(@PathVariable String day){
        //查询同一天内的注册人数
        Integer num = ucenterMemberService.countRegisterNum(day);
        return num;
    }
}

