package com.atguigu.ucenterservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.UcenterMemberMapper;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-04-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }

        //获取会员
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember member=baseMapper.selectOne(wrapper);

        System.out.println(MD5.encrypt(password));
        //校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new GuliException(20001,"密码错误");
        }

        // 是否被禁用
        if(member.getIsDeleted()){
            throw new GuliException(20001,"用户被禁用");
        }

        //使用JWT生成token字符串
        String token= JwtUtils.getJwtToken(member.getId(),member.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息进行校验
        String nickname=registerVo.getNickname();
        String mobile=registerVo.getMobile();
        String password=registerVo.getPassword();
        String code=registerVo.getCode();

        //参数校验
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)){
            throw new GuliException(20001,"注册失败");
        }

        //验证验证码
        //从redis中取出发送的验证码
        //String moblecode= (String) redisTemplate.opsForValue().get(mobile);
        //if(!code.equals(moblecode)){
        //    throw new GuliException(20001,"验证码错误");
        //}

        //查询数据库中是否存在相同的手机号码
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count=baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"手机号已存在");
        }

        //添加信息到数据库
        UcenterMember ucenterMember=new UcenterMember();
        ucenterMember.setNickname(nickname);
        ucenterMember.setMobile(mobile);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://auroramanitoedu.oss-cn-beijing.aliyuncs.com/2021/04/05/18c3405b661a4d7b8cfe8830732b574cfile.png");
        this.save(ucenterMember);
    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    //查询同一天内的注册人数
    @Override
    public Integer countRegisterNum(String day) {
        return baseMapper.countRegisterNum(day);
    }
}
