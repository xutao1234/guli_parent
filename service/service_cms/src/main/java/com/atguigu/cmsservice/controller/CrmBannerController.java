package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-03
 * 前台对banner处理
 */
@RestController
@RequestMapping("/cmsservice/bannerfront")
@CrossOrigin //跨域
public class CrmBannerController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有banner图
    @GetMapping("getBannerList")
    public R getBannerList(){
        List<CrmBanner> list=bannerService.getAllBanner();
        return R.OK().data("bannerList",list);
    }

}

