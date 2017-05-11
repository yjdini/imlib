package com.ini.service;

import com.ini.dao.entity.Apply;
import com.utils.ConstJson;
import com.utils.ResultMap;

import java.util.List;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public interface ApplyService {
    public ResultMap addApply(Apply apply);

    public ResultMap getApplys();

    public ResultMap getApplyDetail(Integer applyId);

    public ResultMap getLatestApply();
}
