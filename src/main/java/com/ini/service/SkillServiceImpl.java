package com.ini.service;

import com.ini.entity.Skill;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public class SkillServiceImpl implements SkillService{
    @Override
    public ConstJson.Result addSkill(Skill skill) {
        return null;
    }

    @Override
    public ConstJson.Result editSkill(Skill skill, Integer userId) {
        return null;
    }

    @Override
    public ConstJson.Result deleteSkill(Integer skillId, Integer userId) {
        return null;
    }

    @Override
    public List<Skill> getSkills(Integer sessionUid) {
        return null;
    }

    @Override
    public Skill getSkillDetail(Integer skillId) {
        return null;
    }

    @Override
    public List<Skill> searchSkills(String keyword) {
        return null;
    }
}
