package com.ini.controllers;

import com.ini.dao.entity.Comment;
import com.ini.service.CommentService;
import com.utils.ConstJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
@RestController
@RequestMapping("/api/skill")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ConstJson.Result addComment(@RequestBody Comment comment, HttpServletRequest request, HttpServletResponse response)
    {
        return commentService.addComment(comment);
    }

    @RequestMapping(value = "/comments/{skillId}")
    public List<?> getComments(@PathVariable Integer skillId){
        return commentService.getCommentsBySkillId(skillId);
    }
}
