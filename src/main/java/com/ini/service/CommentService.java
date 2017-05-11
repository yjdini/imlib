package com.ini.service;

import com.ini.dao.entity.Comment;
import com.utils.ResultMap;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface CommentService {
    ResultMap addComment(Comment comment);

    ResultMap getCommentsBySkillId(Integer skillId);
}
