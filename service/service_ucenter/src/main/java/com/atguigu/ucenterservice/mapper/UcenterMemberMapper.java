package com.atguigu.ucenterservice.mapper;

import com.atguigu.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-04-05
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterNum(String day);
}
