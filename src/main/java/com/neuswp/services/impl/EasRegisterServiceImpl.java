package com.neuswp.services.impl;

import com.neuswp.mappers.EasRegisterMapper;
import com.neuswp.services.EasRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class EasRegisterServiceImpl implements EasRegisterService {
    @Autowired
    private EasRegisterMapper easRegisterMapper;
}
