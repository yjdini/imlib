package com.ini.service;

import com.ini.entity.Apply;
import com.utils.ConstJson;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public interface ApplyService {
    public ConstJson.Result addApply(Apply apply);

    public List<Apply> getApplys(Integer sessionUid);

    public Apply getApplyDetail(Integer applyId);
}
