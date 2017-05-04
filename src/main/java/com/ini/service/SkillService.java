package com.ini.service;

import com.ini.entity.Order;
import com.ini.entity.Skill;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface SkillService {
    ConstJson.Result addSkill(Skill skill);

    ConstJson.Result editSkill(Skill skill, Integer userId);

    ConstJson.Result deleteSkill(Integer skillId, Integer userId);

    List<Skill> getSkills(Integer sessionUid);

    Skill getSkillDetail(Integer skillId);

    List<Skill> searchSkills(String keyword);


}
