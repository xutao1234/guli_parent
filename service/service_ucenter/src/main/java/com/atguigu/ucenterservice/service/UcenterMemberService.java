package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-04-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    // 会员登录
    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    //查询同一天内的注册人数
    Integer countRegisterNum(String day);
}
