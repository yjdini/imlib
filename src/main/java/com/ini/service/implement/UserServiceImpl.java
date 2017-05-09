package com.ini.service.implement;

import com.ini.dao.entity.User;
import com.ini.service.UserService;
import com.utils.ConstJson;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * see UserService
 * Created by Somnus`L on 2017/4/5.
 */

public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ConstJson.Result addUser(User user) {
        try{
            entityManager.persist(user);
        }catch ( Exception e ) {
            e.printStackTrace();
            return ConstJson.ERROR;
        }

        return ConstJson.OK.setResult(user.getUserId().toString());
    }

    @Override
    @Transactional
    public ConstJson.Result updateUser(User user) {
        try{
            entityManager.merge(user);
        }catch ( Exception e ) {
            return ConstJson.ERROR;
        }

        return ConstJson.OK;
    }

    @Override
    public User validateUser(String nickname, String password) {
        User user = (User)entityManager.createQuery(
                "from User where nickname = :nickname and password = :password and status = 1")
                .setParameter("nickname", nickname)
                .setParameter("password", password)
                .getSingleResult();
        return user;
    }


    @Override
    public User getUserById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public ConstJson.Result uploadAvatar(String image) {
        //TODO
        return null;
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

}
