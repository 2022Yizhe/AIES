package com.neuswp.mappers;

import com.neuswp.entity.EasStudent;
import com.neuswp.entity.dto.Student;
import com.neuswp.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface EasStudentMapper {
//    EasStudent getByUsername(String username);

    void insertBatch(List<Student> students);

    // 直接查找
    List<EasStudent> getList (EasStudent easStudent);

    // 包含两个一对一关系
    List<EasStudent> findList(EasStudent easStudent);

    List<EasStudent> findListByUsername(String username);

    EasStudent getStudentByUsername(String username);

    void updateStudent(EasStudent easStudent);

    int getCountBytIdandcId(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,@Param("classId") Integer classId);

    int getEndingCountBytIdandcId(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,@Param("classId") Integer classId);

    List<EasStudent> getStudentScoreListByTid(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,
                                              @Param("classId") Integer classId,@Param("pageUtil") PageUtil pageUtil);

    List<EasStudent> getStudentSelectCourseListByTid(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,
                                                     @Param("classId") Integer classId,@Param("pageUtil") PageUtil pageUtil);


    int getTotal();

    int getTotalSex(String sex);

    void addUsername(String username);

    void deleteStudent(String username);
}
