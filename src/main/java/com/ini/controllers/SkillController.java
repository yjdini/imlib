package com.ini.controllers;

import com.ini.dao.entity.Skill;
import com.ini.dao.schema.SkillTagSet;
import com.ini.service.SkillService;
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
 * Created by Somnus`L on 2017/4/4.
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


    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addSkill(@RequestBody Skill skill, HttpServletRequest request, HttpServletResponse response)
    {
        //防止恶意为别人创建技能
        skill.setUserId(sessionUtil.getUserId(request));
        return skillService.addSkill(skill);
    }

    @RequestMapping(value ="/delete/{skillId}")
    public ConstJson.Result deleteSkill(@PathVariable Integer skillId, HttpServletRequest request){
        return skillService.deleteSkill(skillId, sessionUtil.getUserId(request));
    }

    @RequestMapping("/list/{userId}")
    public List<SkillTagSet> getSkills(@PathVariable Integer userId){
        return skillService.getSkillsByUserId(userId);
    }

    @RequestMapping("/info/{skillId}")
    public SkillTagSet getSkillDetail(@PathVariable Integer skillId){
        return skillService.getSkillDetail(skillId);
    }

    @RequestMapping("/search/keyword/{subId}/{keyword}")
    public List searchByKeyword(@PathVariable String keyword, @PathVariable Integer subId){
        return skillService.searchByKeyword(keyword, subId);
    }

    @RequestMapping("/search/tag/{subId}/{tagId}")
    public List searchByTagId(@PathVariable Integer tagId, @PathVariable Integer subId){
        return skillService.searchByTagId(tagId, subId);
    }

}
