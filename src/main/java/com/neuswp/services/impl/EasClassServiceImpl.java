package com.neuswp.services.impl;

import com.neuswp.entity.EasClass;
import com.neuswp.mappers.EasClassMapper;
import com.neuswp.services.EasClassService;
import com.neuswp.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class EasClassServiceImpl implements EasClassService {

    @Autowired
    private EasClassMapper easClassMapper;

    @Override
    public List<EasClass> getAll() {
        return easClassMapper.getAll();
    }

    @Override
    public int getCount() {
        return easClassMapper.getCount();
    }

    @Override
    public List<EasClass> getList(EasClass easClass, PageUtil pageUtil) {
        return easClassMapper.getList(easClass,pageUtil);
    }

    @Override
    public List<EasClass> findClassName(String classes) {
        return easClassMapper.findClassName(classes);
    }

    @Override
    public void addClass(EasClass easClass) {
        easClassMapper.addClass(easClass);
    }

    @Override
    public void batchDeleteClass(Integer[] ids) {
        easClassMapper.batchDeleteClass(ids);
    }

    @Override
    public EasClass getClassView(Integer id) {
        return easClassMapper.getClassView(id);
    }

    @Override
    public void updateClass(EasClass easClass) {
        easClassMapper.updateClass(easClass);
    }
}
