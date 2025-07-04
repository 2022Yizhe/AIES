package com.neuswp.services;

import com.neuswp.entity.EasBaseCourse;
import com.neuswp.utils.PageUtil;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/21 21:37
 */
public interface EasBaseCourseService {
    List<EasBaseCourse> getAll();

    List<EasBaseCourse> getListById(Integer id) throws Exception;

    EasBaseCourse getBaseCourseById(Integer id) throws Exception;

    List<EasBaseCourse> getList(EasBaseCourse easBaseCourse, PageUtil pageUtil) throws Exception;

    int getCount();

    List<EasBaseCourse> findBaseCourseName(String baseCourseName);

    void addBaseCourse(EasBaseCourse easBaseCourse) throws Exception;

    void batchDeleteBaseCourse(Integer[] ids);

    EasBaseCourse getBaseCourseView(Integer id);

    void updateBaseCourse(EasBaseCourse easBaseCourse);
}
