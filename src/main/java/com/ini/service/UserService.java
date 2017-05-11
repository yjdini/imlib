package com.ini.service;

import com.ini.dao.entity.User;
import com.utils.ConstJson;
import com.utils.ResultMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Somnus`L on 2017/4/5.
 */
public interface UserService {

    ResultMap addUser(User user);

    ResultMap updateUser(User user);

    User validateUser(String nickname, String password);

    ResultMap getUserById(Integer userId);

    User getUser();

    void increaseOrderTimes(Integer userId);

    void increaseOrderedTimes(Integer toUserId);

    ResultMap uploadAvatar(MultipartFile image);

    ResultMap uploadStudentCard(MultipartFile image);

    boolean isMaster();
}
