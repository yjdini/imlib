package com.ini.controllers;

import com.ini.data.entity.Admin;
import com.ini.data.entity.OpenSub;
import com.ini.data.entity.Sub;
import com.ini.data.jpa.AdminRepository;
import com.ini.data.jpa.OpenSubRepository;
import com.ini.data.jpa.SubRepository;
import com.ini.utils.MD5Util;
import com.ini.utils.Map2Bean;
import com.ini.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/13.
 *
 */
@RestController
@RequestMapping("/api/opensub")
public class OpenSubController {
    @Autowired private OpenSubRepository openSubRepository;
    @Autowired private SubRepository subRepository;
    @Autowired private AdminRepository adminRepository;

    @Transactional
    @RequestMapping(value = "/add")
    public Map add(@RequestBody Map<String, Object> body)
    {
        OpenSub openSub = Map2Bean.convert(body, new OpenSub(true));
        openSubRepository.save(openSub);
        return ResultMap.ok().result(openSub.getOpenSubId()).getMap();
    }

}
