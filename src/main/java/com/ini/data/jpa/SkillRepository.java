package com.ini.data.jpa;

import com.ini.data.entity.Skill;
import com.ini.data.schema.SkillTagSet;
import com.ini.data.schema.SkillUserTagSet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface SkillRepository extends JpaRepository<Skill, Integer>, QueryByExampleExecutor<Skill> {

    @Query("select new com.ini.data.schema.SkillTagSet(s, t) " +
            " from Skill s, Tag t where s.userId = ?1 and s.status = 1 and s.tagId = t.tagId")
    List<SkillTagSet> getSkillTags(Integer userId);

    Integer countBySubId(Integer subId);


    @Modifying
    @Query("UPDATE Skill s SET s.status=2 WHERE s.userId=?1 and s.status = 1")
    void shelveSkills(Integer userId);

    @Modifying
    @Query("UPDATE Skill s SET s.status=1 WHERE s.userId=?1 and s.status = 2")
    void groundSkills(Integer userId);

    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t) from Skill s, Tag t, User u " +
            " where s.tagId = t.tagId and s.userId = u.userId and u.userId=?1 " +
            " and s.status = 1 and u.status = 1 and s.skillId != ?2 order by s.createTime desc")
    List<SkillUserTagSet> getSkillsByUserIdExcept(Integer userId, Integer skillId);

    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t) from Skill s, Tag t, User u " +
            " where s.tagId = t.tagId and s.userId = u.userId and u.userId=?1 " +
            " and s.status = 1 and u.status = 1 order by s.createTime desc")
    List<SkillUserTagSet> getSkillsByUserId(Integer userId);

    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
            " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
            " and s.status = 1 and u.status = 1 and u.subId = ?1 " +
            " order by s.seoScore desc")
    List<SkillUserTagSet> getHotest(Integer subId, Pageable pageRequest);

    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
            " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
            " and s.status = 1 and u.status = 1 and u.subId = ?1 and t.tagId = ?2 " +
            " order by s.seoScore desc")
    List<SkillUserTagSet> getHotest(Integer subId, Integer tagId, Pageable pageRequest);


    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
            " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
            " and s.status = 1 and u.status = 1 and u.subId = ?2 and " +
            "(u.name like  %?1% or s.title like %?1% )  order by s.createTime desc")
    List<SkillUserTagSet> pageQueryByKeyword(String keyword, Integer subId, Pageable pageRequest);

    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
            " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
            " and s.status = 1 and u.status = 1 and u.subId = ?2 and s.tagId = ?1 " +
            " order by s.createTime desc")
    List<SkillUserTagSet> pageQueryByTagId(Integer tagId, Integer subId, Pageable pageRequest);

    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t)" +
            " from Skill s, Tag t, User u where s.tagId = t.tagId and s.userId = u.userId " +
            " and s.status = 1 and u.status = 1 and u.subId = ?1" +
            " order by s.createTime desc")
    List<SkillUserTagSet> pageQueryBySubId(Integer subId, Pageable pageRequest);

    @Query("select new com.ini.data.schema.SkillUserTagSet(s, u, t) from Skill s, Tag t, User u " +
            " where s.tagId = t.tagId and s.userId = u.userId and s.skillId=?1 " +
            " order by s.createTime desc")
    SkillUserTagSet getSkillBySkillId(Integer skillId);
}
