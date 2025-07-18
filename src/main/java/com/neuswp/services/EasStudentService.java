package com.neuswp.services;

import com.neuswp.entity.EasStudent;
import com.neuswp.utils.PageUtil;

import java.util.List;


public interface EasStudentService {
    List<EasStudent> getList(EasStudent easStudent) throws Exception;

    List<EasStudent> findList(EasStudent easStudent) throws Exception;

    List<EasStudent> findListByUsername(String username) throws Exception;

    EasStudent getStudentByUsername(String username) throws Exception;

    void updateStudent(EasStudent easStudent) throws Exception;

    int getCountBytIdandcId(Integer tId, Integer baseCourseId,Integer classId) throws Exception;

    int getEndingCountBytIdandcId(Integer tId, Integer baseCourseId, Integer classId);

    List<EasStudent> getStudentScoreListByTid(Integer tId, Integer baseCourseId,Integer classId, PageUtil pageUtil);

    List<EasStudent> getStudentSelectCourseListByTid(Integer tId, Integer baseCourseId, Integer classId, PageUtil pageUtil);


    int getTotal();

    int getTotalSex(String sex);

    void addUsername(String username)  throws Exception;

    void deleteStudent(String username) throws Exception;

    void importStudentsFromOSS(String fileUrl);

    boolean hasStudent(String username);
}
