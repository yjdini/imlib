package com.ini.service.abstrac;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 */
public interface RootService {

    Map login(String name, String password);

    Map getSubList();

    Map closeSub(Integer subId, String reason, String closeReason);

    Map getOpenSubList(Integer result);

    Map getSystemInfo();

    Map startSub(Integer subId);
}
