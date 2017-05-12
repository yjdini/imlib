package com.ini.service.abstrac;

import com.ini.data.entity.Apply;
import com.ini.utils.ResultMap;

/**
 * Created by Somnus`L on 2017/5/4.
 */
public interface ApplyService {
    public ResultMap addApply(Apply apply);

    public ResultMap getApplys();

    public ResultMap getApplyDetail(Integer applyId);

    public ResultMap getLatestApply();
}
