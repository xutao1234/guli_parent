package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

/**
 * @Author: taotao
 * @Date: 2021/4/3
 * @Version 1.0
 * 后台对banner处理，crud
 */
@RestController
@RequestMapping("/cmsservice/banneradmin")
@CrossOrigin //跨域
public class CrmAdminBannerController {
    @Autowired
    private CrmBannerService bannerService;

    //查询banner图
    @GetMapping("getBannerPage/{page}/{limit}")
    public R getBannerPage(@PathVariable Long page, @PathVariable Long limit){
        Page<CrmBanner> pageParam=new Page<>(page,limit);
        bannerService.page(pageParam,null);
        return R.OK().data("items",pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.OK();
    }

    //查询banner
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable String id){
        CrmBanner crmBanner = bannerService.getById(id);
        return R.OK().data("banner",crmBanner);
    }
    //修改banner
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return R.OK();
    }
    //删除banner
    @DeleteMapping("{id}")
    public R deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return R.OK();
    }
}
