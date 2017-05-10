package com.ini.service;

import com.ini.dao.entity.Skill;
import com.ini.dao.schema.SkillTagSet;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface SkillService {
    ConstJson.Result addSkill(Skill skill);

    ConstJson.Result deleteSkill(Integer skillId, Integer userId);

    List<SkillTagSet> getSkillsByUserId(Integer userId);

    SkillTagSet getSkillDetail(Integer skillId);

    List searchByKeyword(String keyword, Integer subId);

    List searchByTagId(Integer tagId, Integer subId);

    void increaseOrderTimes(Integer skillId);

    void increaseOrderedTimes(Integer skillId);

    List searchAll(Integer subId);
}
