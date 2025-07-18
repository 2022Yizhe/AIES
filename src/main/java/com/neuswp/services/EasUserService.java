package com.neuswp.services;

import com.neuswp.entity.EasUser;

import java.util.List;



public interface EasUserService {

    void addUser(EasUser user) throws Exception;

    String findUsernameById(int userid) throws Exception;

    void addUserRole(EasUser user, Integer[] ids) throws Exception;

    void deleteUserRole(Integer id) throws Exception;

    void updateUser(EasUser user) throws Exception;

    List<EasUser> findUserName(String username) throws Exception;

    Integer findRoleIdByUserId(Integer id) throws Exception;

    int getCount();

    List<Integer> findRoleIdByUserId2(Integer id);

    int getRoleCountByUid(Integer Uid)throws Exception;
}
