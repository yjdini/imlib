package com.ini.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Somnus`L on 2017/4/4.
 */
@RestController
@RequestMapping("/api/skill")
public class SkillController {

    @RequestMapping("/getUserSkills")
    public void searchUserSkills(){

    }

    @RequestMapping("/showDetail/{id}")
    public void showDetail(@PathVariable int id){

    }

    @RequestMapping("/searchSkill/{keyword}")
    public void searchByKeyword(@PathVariable String keyword){

    }

    @RequestMapping(value ="/addSkill",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void add(){

    }

    @RequestMapping("/deleteSkill/{id}")
    public void delete(@PathVariable int id){

    }



}
