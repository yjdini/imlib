package com.ini.service.implement;

import com.ini.dao.entity.Comment;
import com.ini.service.CommentService;
import com.utils.ResultMap;
import com.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 *
 */
public  class CommentServiceImpl implements CommentService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    SessionUtil sessionUtil;

    @Override
    @Transactional
    public ResultMap addComment(Comment comment) {
//        entityManager.createNamedQuery("").
        try {
            comment.setUserId(sessionUtil.getUserId());
            entityManager.persist(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok().put("result", comment.getCommentId());
    }

    @Override
    public ResultMap getCommentsBySkillId(Integer skillId) {
        List comments =  entityManager.createQuery("select new com.ini.dao.schema.CommentUserSet(c,u) from " +
                " Comment c, User u where c.userId = u.userId and u.status =1 and c.status = 1 " +
                " and c.skillId = :skillId")
                .setParameter("skillId", skillId)
                .getResultList();
        return ResultMap.ok().put("result", comments);
    }
}
