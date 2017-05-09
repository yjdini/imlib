package com.ini.controllers;

import com.ini.entity.Apply;
import com.ini.service.ApplyService;
import com.ini.service.UserService;
import com.utils.ConstJson;
import com.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addApply(@RequestBody Apply apply, HttpServletRequest request, HttpServletResponse response)
    {
        //防止恶意为别人创建技能
        apply.setUserId(sessionUtil.getUserId(request));
        return applyService.addApply(apply);
    }

    @RequestMapping("/latest")
    public Apply getLatestApply(HttpServletRequest request){
        return applyService.getLatestApply(sessionUtil.getUserId(request));
    }

    @RequestMapping(value = "/list")
    public List<Apply> getApplys(HttpServletRequest request)
    {
        return applyService.getApplys(sessionUtil.getUserId(request));
    }

    @RequestMapping("/info/{applyId}")
    public Apply getApplyDetail(@PathVariable Integer applyId){
        return applyService.getApplyDetail(applyId);
    }

}
