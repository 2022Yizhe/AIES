package com.neuswp.services;

import com.neuswp.entity.EasCourse;
import com.neuswp.utils.PageUtil;

import java.util.List;



public interface EasCourseService {
    int getCount();

    List<EasCourse> getList(EasCourse easCourse, PageUtil pageUtil) throws Exception;

    List<EasCourse> findCourseByBaseCourseIdAndTeacherId(Integer baseCourseId, Integer tId);

    int addCourse(EasCourse easCourse);

    EasCourse getCourseById(Integer id) throws Exception;

    void updateCourseById(EasCourse easCourse);

    void batchDeleteCourse(Integer[] ids);

    int getCountBytId(Integer tId);

    List<EasCourse> getCourseListBytId(Integer tId, PageUtil pageUtil);

    int completeCourse(EasCourse easCourse);

    int getTotalItemsCountBySid(int isAll, String searchKey, int sId);

    List<EasCourse> getCourseListBySid(int isAll, String searchKey, int sId, PageUtil pageUtil);

    int getTotalPass(Integer baseCourseId);

    int getTotalNoPass(Integer baseCourseId);
}
