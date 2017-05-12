package com.ini.data.jpa;

import com.ini.data.entity.Skill;
import com.ini.data.schema.SkillTagSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/12.
 *
 */
public interface SkillRepository extends JpaRepository<Skill, Integer>, QueryByExampleExecutor<Skill> {

    List<SkillTagSet> getSkillTags();
}
