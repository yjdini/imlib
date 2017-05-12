package com.ini.data.schema;

import com.ini.data.entity.Skill;
import com.ini.data.entity.Tag;
/**
 * Created by Somnus`L on 2017/5/10.
 *
 */
public class SkillTagSet {
    private Skill skill;
    private String tagName;

    public SkillTagSet(Skill skill, Tag tag) {
        this.skill = skill;
        this.tagName = tag.getName();
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
