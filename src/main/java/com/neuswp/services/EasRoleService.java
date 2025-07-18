package com.neuswp.services;

import com.neuswp.entity.EasRole;

import java.util.List;



public interface EasRoleService {
    List<EasRole> getAll();

    String findRoleNameByRoleId(Integer roleid) throws Exception;
}
