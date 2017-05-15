package com.ini.service.abstrac;

import com.ini.data.entity.Skill;
import com.ini.utils.ResultMap;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface SkillService {
    ResultMap addSkill(Skill skill);

    ResultMap deleteSkill(Integer skillId);

    ResultMap getSkillsByUserId(Integer userId);

    ResultMap getSkillDetail(Integer skillId);

    ResultMap searchByKeyword(String keyword, Integer subId);

    ResultMap searchByTagId(Integer tagId, Integer subId);

    void increaseOrderTimes(Integer skillId);

    void increaseOrderedTimes(Integer skillId);

    ResultMap searchAll(Integer subId);

    ResultMap getSkillsByUserIdExcept(Integer userId, Integer exceptSkillId);

    void addScore(Integer skillId, Integer score);

    ResultMap searchHotest(Integer subId);

    void increaseShowTime(Integer skillId);

    ResultMap searchHotest(Integer subId, Integer tagId);

}
