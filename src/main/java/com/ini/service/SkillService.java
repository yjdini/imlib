package com.ini.service;

import com.ini.entity.Skill;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface SkillService {
    ConstJson.Result addSkill(Skill skill);

    ConstJson.Result deleteSkill(Integer skillId, Integer userId);

    List<Skill> getSkillsByUserId(Integer userId);

    Skill getSkillDetail(Integer skillId);

    List<Skill> searchByKeyword(String keyword, Integer subId);

    List<Skill> searchByTagId(Integer tagId, Integer subId);

    void increaseOrderTimes(Integer skillId);

    void increaseOrderedTimes(Integer skillId);
}
