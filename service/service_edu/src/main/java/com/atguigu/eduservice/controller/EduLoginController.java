package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: taotao
 * @Date: 2021/3/22
 * @Version 1.0
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin // 解决跨域
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){
        return R.OK().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return R.OK().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
