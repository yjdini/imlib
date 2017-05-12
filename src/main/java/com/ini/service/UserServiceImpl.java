package com.ini.service;

import com.ini.dao.entity.User;
import com.ini.dao.utils.EntityUtil;
import com.ini.service.abstrac.UserService;
import com.ini.utils.FileUploadUtil;
import com.ini.utils.ResultMap;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * see UserService
 * Created by Somnus`L on 2017/4/5.
 */

public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private SessionUtil sessionUtil;

    @Override
    @Transactional
    public ResultMap addUser(User user) {
        try{
            if (userNameExist(user.getName()))
                return ResultMap.error().setMessage("nameExist");
            user.setStudentCard((String) sessionUtil.get("studentCard"));
            sessionUtil.set("studentCard", null);
            entityManager.persist(user);
            sessionUtil.setUser(user);
        }catch ( Exception e ) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok().result("userId",user.getUserId())
                            .result("subId", user.getSubId())
                            .result("type", user.getType());
    }

    private boolean userNameExist(String name) {
        List result = entityManager.createQuery("from User where nickname = :nickname")
                .setParameter("nickname", name)
                .getResultList();
        return result.size() != 0;
    }

    @Override
    @Transactional
    public ResultMap updateUser(User user) {
        try{
            entityManager.merge(user);
        }catch ( Exception e ) {
            return ResultMap.error().setMessage(e.getMessage());
        }

        return ResultMap.ok();
    }

    @Override
    public User validateUser(String nickname, String password) {
        List users = entityManager.createQuery(
                "from User where nickname = :nickname and password = :password and status = 1")
                .setParameter("nickname", nickname)
                .setParameter("password", password)
                .getResultList();
        if (users.size() == 0)
            return null;
        else {
            return (User)users.get(0);
        }
    }


    @Override
    public ResultMap getUserById(Integer userId) {
        User user = entityManager.find(User.class, userId);
        return  ResultMap.ok().result(
                EntityUtil.all(user).remove("password").getMap());
    }

    @Override
    public User getUser() {
        return entityManager.find(User.class, sessionUtil.getUserId());
    }

    @Override
    public void increaseOrderTimes(Integer userId) {
        entityManager.createNativeQuery(
                "update User set orderTimes = orderTimes+1 where userId = :userId")
                .setParameter("userId", userId).executeUpdate();
    }

    @Override
    public void increaseOrderedTimes(Integer userId) {
        entityManager.createNativeQuery(
                "update User set orderedTimes = orderedTimes+1 where userId = :userId")
                .setParameter("userId", userId).executeUpdate();
    }

    @Override
    public ResultMap uploadAvatar(MultipartFile image) {
        System.out.print(image.getContentType());
        String fileUrl;
        try {
            fileUrl = fileUploadUtil.saveFile(image);
            setUserAvatar(sessionUtil.getUserId(), fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
        return ResultMap.ok().put("result", fileUrl);
    }

    /**
     * upload and save studentCard to user
     * @param image
     * @return
     */
    @Override
    public ResultMap uploadStudentCard(MultipartFile image) {
        try {
            String fileUrl = fileUploadUtil.saveFile(image);
            if (sessionUtil.logined()) {
                User user = entityManager.find(User.class, sessionUtil.getUserId());
                user.setStudentCard(fileUrl);
                entityManager.merge(user);
                sessionUtil.setUser(user);
            } else {
                sessionUtil.set("studentCard", fileUrl);
            }
            return ResultMap.ok().put("result", fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMap.error().setMessage(e.getMessage());
        }
    }

    @Override
    public boolean isMaster() {
        return entityManager.find(User.class, sessionUtil.getUserId()).getType().equals("m");
    }

    @Transactional
    private void setUserAvatar(Integer userId, String fileUrl) {
        User user = entityManager.find(User.class, userId);
        user.setAvatar(fileUrl);
        entityManager.merge(user);
        sessionUtil.setUser(user);
    }

}
