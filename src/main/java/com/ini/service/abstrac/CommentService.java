package com.ini.service.abstrac;

import com.ini.utils.ResultMap;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface CommentService {

    ResultMap getCommentsBySkillId(Integer skillId);

    ResultMap addComment(Integer orderId, Integer score, String content);
}
