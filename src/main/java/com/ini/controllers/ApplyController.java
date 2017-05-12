package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.data.entity.Apply;
import com.ini.service.abstrac.ApplyService;
import com.ini.service.abstrac.UserService;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/4.
 */
@RestController
@RequestMapping("/api/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionUtil sessionUtil;

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/add", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map addApply(@RequestBody Apply apply)
    {
        //防止恶意为别人创建申请
        apply.setUserId(sessionUtil.getUserId());
        return applyService.addApply(apply).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping("/latest")
    public Map getLatestApply(){
        return applyService.getLatestApply().getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/list")
    public Map getApplys()
    {
        return applyService.getApplys().getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping("/info/{applyId}")
    public Map getApplyDetail(@PathVariable Integer applyId){
        return applyService.getApplyDetail(applyId).getMap();
    }

}
