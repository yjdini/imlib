package com.ini.service.implement;

import com.ini.dao.entity.Comment;
import com.ini.service.CommentService;
import com.utils.ConstJson;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 *
 */
public  class CommentServiceImpl implements CommentService {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public ConstJson.Result addComment(Comment comment) {
        try {
            entityManager.persist(comment);
        } catch (Exception e) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }
        return ConstJson.OK.setResult(comment.getSkillId().toString());
    }

    @Override
    public List<?> getCommentsBySkillId(Integer skillId) {
        return entityManager.createQuery(
                "from Comment where skillId = :skillId and status = 1", Comment.class)
                .setParameter("skillId", skillId)
                .getResultList();
    }
}
