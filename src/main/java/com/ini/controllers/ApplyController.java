package com.ini.controllers;

import com.ini.entity.Apply;
import com.ini.service.ApplyService;
import com.ini.service.UserService;
import com.utils.ConstJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addApply",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addApply(@RequestBody Apply apply, HttpServletRequest request, HttpServletResponse response)
    {
        //防止恶意为别人创建技能
        apply.setUserId(userService.getSessionUid(request));
        return applyService.addApply(apply);
    }

    @RequestMapping(value = "/getApplys")
    public List<Apply> getApplys(HttpServletRequest request)
    {
        return applyService.getApplys(userService.getSessionUid(request));
    }

    @RequestMapping("/getApplyDetail/{applyId}")
    public Apply getApplyDetail(@PathVariable Integer applyId){
        return applyService.getApplyDetail(applyId);
    }

}
