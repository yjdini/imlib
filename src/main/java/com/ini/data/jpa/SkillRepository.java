package com.ini.data.jpa;

import com.ini.data.entity.Skill;
import com.ini.data.schema.SkillTagSet;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
