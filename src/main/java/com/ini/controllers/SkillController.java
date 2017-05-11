package com.ini.controllers;

import com.ini.aop.annotation.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.dao.entity.Skill;
import com.ini.service.SkillService;
import com.ini.service.UserService;
import com.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/4/4.
 *
 */
@RestController
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionUtil sessionUtil;

    @Authentication(value = AuthenticationType.Master)
    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map addSkill(@RequestBody Skill skill)
    {
        //防止恶意为别人创建技能
        return skillService.addSkill(skill).getMap();
    }

    @Authentication(value = AuthenticationType.Master)
    @RequestMapping(value ="/delete/{skillId}")
    public Map deleteSkill(@PathVariable Integer skillId){
        return skillService.deleteSkill(skillId).getMap();
    }

    @RequestMapping("/list/{userId}")
    public Map getSkills(@PathVariable Integer userId){
        return skillService.getSkillsByUserId(userId).getMap();
    }

    @RequestMapping("/list/{userId}/{exceptSkillId}")
    public Map getSkills(@PathVariable Integer userId, @PathVariable Integer exceptSkillId){
        return skillService.getSkillsByUserIdExcept(userId, exceptSkillId).getMap();
    }

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping("/list")
    public Map getSkill(){
        return skillService.getSkillsByUserId(sessionUtil.getUserId()).getMap();
    }


    @RequestMapping("/info/{skillId}")
    public Map getSkillDetail(@PathVariable Integer skillId){
        return skillService.getSkillDetail(skillId).getMap();
    }

    @RequestMapping("/search/keyword/{subId}/{keyword}")
    public Map searchByKeyword(@PathVariable String keyword, @PathVariable Integer subId){
        return skillService.searchByKeyword(keyword, subId).getMap();
    }

    @RequestMapping("/search/tag/{subId}/{tagId}")
    public Map searchByTagId(@PathVariable Integer tagId, @PathVariable Integer subId){
        return skillService.searchByTagId(tagId, subId).getMap();
    }

    @RequestMapping("/search/all/{subId}")
    public Map searchAll(@PathVariable Integer subId){
        return skillService.searchAll(subId).getMap();
    }

}
