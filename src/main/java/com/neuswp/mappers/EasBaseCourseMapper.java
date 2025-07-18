package com.neuswp.mappers;

import com.neuswp.entity.EasBaseCourse;
import com.neuswp.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface EasBaseCourseMapper {
    List<EasBaseCourse> getAll();

    List<EasBaseCourse> getListById(Integer id);

    EasBaseCourse getBaseCourseById(Integer id);

    List<EasBaseCourse> getList(@Param("easBaseCourse") EasBaseCourse easBaseCourse,@Param("pageUtil") PageUtil pageUtil);

    int getCount();

    List<EasBaseCourse> findBaseCourseName(String baseCourseName);

    void addBaseCourse(EasBaseCourse easBaseCourse);

    void batchDeleteBaseCourse(Integer[] ids);

    void updateBaseCourse(EasBaseCourse easBaseCourse);
}
