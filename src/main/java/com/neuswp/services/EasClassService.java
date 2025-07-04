package com.neuswp.services;

import com.neuswp.entity.EasClass;
import com.neuswp.utils.PageUtil;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/15 12:30
 */
public interface EasClassService {
    List<EasClass> getAll();

    int getCount();

    List<EasClass> getList(EasClass easClass, PageUtil pageUtil);

    List<EasClass> findClassName(String classes);

    void addClass(EasClass easClass);

    void batchDeleteClass(Integer[] ids);

    EasClass getClassView(Integer id);

    void updateClass(EasClass easClass);
}
