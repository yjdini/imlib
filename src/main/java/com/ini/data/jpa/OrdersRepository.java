package com.ini.data.jpa;

import com.ini.data.entity.Orders;
import com.ini.data.schema.CommentUserSkillSet;
import com.ini.data.schema.OrderUserSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer>, QueryByExampleExecutor<Orders> {

    @Query("select new com.ini.data.schema.OrderUserSet(o,to,fr,t,s)"+
            " from Orders o,User to,User fr, Tag t,Skill s where o.fromStatus = 1 and " +
            " fr.userId = ?1 and to.userId = o.toUserId and fr.userId = o.fromUserId and " +
            " t.tagId = s.tagId and s.skillId = o.skillId order by o.createTime desc")
    List<OrderUserSet> getExistFromOrders(Integer userId);

    @Query("select new com.ini.data.schema.OrderUserSet(o,to,fr,t,s)"+
            " from Orders o,User to,User fr, Tag t,Skill s where o.toStatus = 1 and " +
            " to.userId = ?1 and to.userId = o.toUserId and fr.userId = o.fromUserId and " +
            " t.tagId = s.tagId and s.skillId = o.skillId order by o.createTime desc")
    List<OrderUserSet> getExistToOrders(Integer userId);

    @Query("select new com.ini.data.schema.OrderUserSet(o,to,fr,t,s)"+
            " from Orders o,User to,User fr, Tag t,Skill s where  " +
            " fr.userId = ?1 and to.userId = o.toUserId and fr.userId = o.fromUserId and " +
            " t.tagId = s.tagId and s.skillId = o.skillId order by o.createTime desc")
    List<OrderUserSet> getAllFromOrders(Integer userId);

    @Query("select new com.ini.data.schema.OrderUserSet(o,to,fr,t,s)"+
            " from Orders o,User to,User fr, Tag t,Skill s where  " +
            " to.userId = ?1 and to.userId = o.toUserId and fr.userId = o.fromUserId and " +
            " t.tagId = s.tagId and s.skillId = o.skillId order by o.createTime desc")
    List<OrderUserSet> getAllToOrders(Integer userId);

    Integer countBySubId(Integer subId);

    Integer countBySubIdAndResult(Integer subId, Integer result);


    @Query("select new com.ini.data.schema.CommentUserSkillSet(o,u,s) from " +
            " Orders o, User u, Skill s where u.userId = ?1 and o.toUserId=u.userId and o.skillId = s.skillId order " +
            " by o.commentTime desc")
    List<CommentUserSkillSet> getCommentsByUserId(Integer userId);
}
