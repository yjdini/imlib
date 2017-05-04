package com.ini.controllers;

import com.ini.entity.Skill;
import com.ini.service.SkillService;
import com.ini.service.UserService;
import com.utils.ConstJson;
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

    @RequestMapping(value = "/addSkill",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addSkill(@RequestBody Skill skill, HttpServletRequest request, HttpServletResponse response)
    {
        //防止恶意为别人创建技能
        skill.setUserId(userService.getSessionUid(request));
        return skillService.addSkill(skill);
    }

    @RequestMapping(value = "/editSkill",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result editUser(@RequestBody Skill skill, HttpServletRequest request)
    {
        return skillService.editSkill(skill, userService.getSessionUid(request));
    }

    @RequestMapping(value ="/deleteSkill/{skillId}")
    public ConstJson.Result deleteSkill(@PathVariable Integer skillId, HttpServletRequest request){
        return skillService.deleteSkill(skillId, userService.getSessionUid(request));
    }

    @RequestMapping("/getSkills/{userId}")
    public List<Skill> getSkills(@PathVariable Integer userId){
        return skillService.getSkills(userId);
    }

    @RequestMapping("/getSkillDetail/{skillId}")
    public Skill getSkillDetail(@PathVariable Integer skillId){
        return skillService.getSkillDetail(skillId);
    }

    @RequestMapping("/search")
    public List<Skill> getSkills(@PathVariable String keyword){
        return skillService.searchSkills(keyword);
    }


}
