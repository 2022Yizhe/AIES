package com.neuswp.services.impl;

import com.neuswp.services.AiQuestionCenterService;
import com.neuswp.utils.VolEngineAI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AiQuestionCenterServiceImpl implements AiQuestionCenterService {

    private VolEngineAI volEngineAI;

    public AiQuestionCenterServiceImpl() {
        try {
            volEngineAI = new VolEngineAI();
        } catch (RuntimeException e){
            volEngineAI = null;
            System.out.println("[Service] VolEngineAI configuration failed!");
        }
    }

    @Override
    public String simpleAskQuestion(String question) {

        // simple ask/reply
        String reply = (volEngineAI == null ? "未成功接入推理模型！" : volEngineAI.SimpleGenerate(question));

        return reply;
    }
}
