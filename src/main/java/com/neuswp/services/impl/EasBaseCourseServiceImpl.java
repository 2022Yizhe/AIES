package com.neuswp.services.impl;

import com.neuswp.entity.EasBaseCourse;
import com.neuswp.mappers.EasBaseCourseMapper;
import com.neuswp.services.EasBaseCourseService;
import com.neuswp.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class EasBaseCourseServiceImpl implements EasBaseCourseService {
    @Autowired
    private EasBaseCourseMapper easBaseCourseMapper;

    @Override
    public List<EasBaseCourse> getAll() {
        return easBaseCourseMapper.getAll();
    }

    @Override
    public List<EasBaseCourse> getListById(Integer id) throws Exception{
        return easBaseCourseMapper.getListById(id);
    }

    @Override
    public EasBaseCourse getBaseCourseById(Integer id) throws Exception {
        return easBaseCourseMapper.getBaseCourseById(id);
    }

    @Override
    public List<EasBaseCourse> getList(EasBaseCourse easBaseCourse, PageUtil pageUtil) throws Exception {
        return easBaseCourseMapper.getList(easBaseCourse,pageUtil);
    }

    @Override
    public int getCount() {
        return easBaseCourseMapper.getCount();
    }

    @Override
    public List<EasBaseCourse> findBaseCourseName(String baseCourseName) {
        return easBaseCourseMapper.findBaseCourseName(baseCourseName);
    }

    @Override
    public void addBaseCourse(EasBaseCourse easBaseCourse) throws Exception{
        easBaseCourseMapper.addBaseCourse(easBaseCourse);
    }

    @Override
    public void batchDeleteBaseCourse(Integer[] ids) {
        easBaseCourseMapper.batchDeleteBaseCourse(ids);
    }

    @Override
    public EasBaseCourse getBaseCourseView(Integer id) {
        return easBaseCourseMapper.getBaseCourseById(id);
    }

    @Override
    public void updateBaseCourse(EasBaseCourse easBaseCourse) {
        easBaseCourseMapper.updateBaseCourse(easBaseCourse);
    }

}
