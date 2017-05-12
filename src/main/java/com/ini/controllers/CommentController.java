package com.ini.controllers;

import com.ini.aop.authentication.Authentication;
import com.ini.aop.authentication.AuthenticationType;
import com.ini.dao.entity.Comment;
import com.ini.service.abstrac.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/4/5.
 */
@RestController
@RequestMapping("/api/skill")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Authentication(value = AuthenticationType.CommonUser)
    @RequestMapping(value = "/comment/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map addComment(@RequestBody Comment comment)
    {
        return commentService.addComment(comment).getMap();
    }

    @RequestMapping(value = "/comments/{skillId}")
    public Map getComments(@PathVariable Integer skillId){
        return commentService.getCommentsBySkillId(skillId).getMap();
    }
}
