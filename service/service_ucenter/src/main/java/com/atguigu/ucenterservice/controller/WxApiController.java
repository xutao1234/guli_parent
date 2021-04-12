package com.atguigu.ucenterservice.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.servicebase.ExceptionHandler.GuliException;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.atguigu.ucenterservice.util.ConstantPropertiesUtil;
import com.atguigu.ucenterservice.util.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author: taotao
 * @Date: 2021/4/6
 * @Version 1.0
 */

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;

    @GetMapping("callback")
    public String callback(String code, String state) {

        //得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);
        //第一步,根据code向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"+
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                                                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                                                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                                                code);
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new GuliException(20001,"获取access_token失败");
        }

        //解析json字符串
        Gson gson=new Gson();
        HashMap map=gson.fromJson(result,HashMap.class);
        String accessToken= (String) map.get("access_token");
        String openid= (String) map.get("openid");

        //验证当前用户是否曾经使用过微信登录
        UcenterMember ucenterMember=memberService.getByOpenid(openid);
        if(ucenterMember == null){
            System.out.println("新用户注册");

            //第2步，根据token获取用户信息，访问微信的资源服务器
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";

            String userInfoUrl = String.format(baseUserInfoUrl,accessToken,openid);
            String resultUserinfo = null;

            try {
                resultUserinfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo============"+resultUserinfo);
            } catch (Exception e) {
                throw new GuliException(20001,"获取用户信息失败");
            }

            //解析json，得到用户信息
            HashMap<String,Object> mapUserInfo=gson.fromJson(resultUserinfo,HashMap.class);
            String nickname= (String) mapUserInfo.get("nickname");
            String headimgurl= (String) mapUserInfo.get("headimgurl");

            //向数据库添加一条记录
            UcenterMember ucenter=new UcenterMember();
            ucenter.setNickname(nickname);
            ucenter.setOpenid(openid);
            ucenter.setAvatar(headimgurl);
            memberService.save(ucenter);

            String token= JwtUtils.getJwtToken(ucenter.getId(),ucenter.getNickname());

            return "redirect:http://localhost:3000?token="+token;
        }
        return "redirect:http://localhost:3000";
    }

    @GetMapping("login")
    public String genQrConnect() {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }
}
