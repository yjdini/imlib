package com.ini.service;

import com.ini.dao.entity.Comment;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface CommentService {
    ConstJson.Result addComment(Comment comment);

    List<?> getCommentsBySkillId(Integer skillId);
}
