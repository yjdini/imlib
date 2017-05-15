package com.ini.data.schema;

import com.ini.data.entity.Admin;

/**
 * Created by Somnus`L on 2017/5/15.
 */
public class SubSet {
    private String schoolName;
    private String token;

    public SubSet(Admin admin) {
        this.schoolName = admin.getSchoolName();
        this.token = admin.getToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSchoolName() {

        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
