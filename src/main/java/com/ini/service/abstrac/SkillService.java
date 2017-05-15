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

    void increaseOrderTimes(Integer skillId);

    void increaseOrderedTimes(Integer skillId);


    ResultMap getSkillsByUserIdExcept(Integer userId, Integer exceptSkillId);

    void addScore(Integer skillId, Integer score);


    void increaseShowTime(Integer skillId);


    ResultMap searchPageByTagId(Integer tagId, Integer subId, Integer currentPage);

    ResultMap searchPageAll(Integer subId, Integer currentPage);

    ResultMap searchPageHotest(Integer subId, Integer currentPage);

    ResultMap searchPageHotest(Integer subId, Integer tagId, Integer currentPage);

    ResultMap searchPageByKeyword(String keyword, Integer subId, Integer currentPage);
}
