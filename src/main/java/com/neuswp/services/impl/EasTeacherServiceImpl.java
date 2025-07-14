package com.neuswp.services.impl;

import com.neuswp.entity.EasTeacher;
import com.neuswp.mappers.EasTeacherMapper;
import com.neuswp.services.EasTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EasTeacherServiceImpl implements EasTeacherService {
    @Autowired
    private EasTeacherMapper easTeacherMapper;

    @Override
    public List<EasTeacher> findTeacherList(EasTeacher easTeacher) throws Exception {
        return easTeacherMapper.findTeacherList(easTeacher);
    }

    @Override
    public List<EasTeacher> findListByUsername(String username) {
        return easTeacherMapper.findListByUsername(username);
    }

    @Override
    public EasTeacher getTeacherByUsername(String username) {
        return easTeacherMapper.getTeacherByUsername(username);
    }

    @Override
    public void updateTeacher(EasTeacher easTeacher) {
        easTeacherMapper.updateTeacher(easTeacher);
    }

    @Override
    public List<EasTeacher> getAll() {
        return easTeacherMapper.getAll();
    }

    @Override
    public EasTeacher findTeacherByUsername(String username) {
        return easTeacherMapper.findTeacherByUsername(username);
    }

    @Override
    public int getTotal() {
        return easTeacherMapper.getTotal();
    }

    @Override
    public void addUsername(String username) {
        easTeacherMapper.addUsername(username);
    }

    @Override
    public void deleteTeacher(String username) {
        easTeacherMapper.deleteTeacher(username);
    }


}
