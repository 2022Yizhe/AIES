package com.neuswp.services.impl;

import com.neuswp.constant.IntendKeyWords;
import com.neuswp.services.AiQuestionCenterService;
import com.neuswp.utils.ExcelExport;
import com.neuswp.utils.VolEngineAI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AiQuestionCenterServiceImpl implements AiQuestionCenterService {

    private VolEngineAI volEngineAI;

    @Autowired
    ExcelExport excelExport;

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

        /// simple ask/reply
        String reply = (volEngineAI == null ? "未成功接入推理模型！" : volEngineAI.SimpleGenerate(question));

        return reply;
    }

    @Override
    public String askQuestion(String question) {
        if (volEngineAI == null)
            return "未成功接入推理模型";

        /// 识别用户意图，进行带有功能调用的对话
        // 1. 意图解析
        String parsedIntend = parseUserInputToIntent(question);

        // 2. 功能调用
        String url = excelExport.recognize(parsedIntend);

        // 3. 对话
        String reply = (url != null) ? "自动导出表格: " + url : volEngineAI.SimpleGenerate(question);

        return reply;
    }


    /**
     * 简单意图识别
     * @param input 用户输入字符串 token
     * @return 意图解析后的结果
     */
    private String parseUserInputToIntent(String input) {
        // 识别出的意图
        if (input.contains("导出") || input.contains("生成") || input.contains("制定"))
            if (input.contains("课程")) {
                return IntendKeyWords.EXPORT_BASE_COURSE_LIST;
            } else if (input.contains("班级")) {
                return IntendKeyWords.EXPORT_CLASS_LIST;
            } else if (input.contains("教师")) {
                return IntendKeyWords.EXPORT_TEACHER_LIST;
            } else if (input.contains("学生")) {
                return IntendKeyWords.EXPORT_STUDENT_LIST;
            }

        // 未识别出的意图
        return IntendKeyWords.ERROR_UNKNOWN_INTEND;
    }

}
