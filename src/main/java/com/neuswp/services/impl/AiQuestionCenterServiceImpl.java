package com.neuswp.services.impl;

import com.neuswp.constant.IntendKeyWords;
import com.neuswp.entity.AiChatHistory;
import com.neuswp.mappers.AiChatHistoryMapper;
import com.neuswp.mappers.EasStudentMapper;
import com.neuswp.mappers.EasUserMapper;
import com.neuswp.services.AiQuestionCenterService;
import com.neuswp.services.EasStudentService;
import com.neuswp.utils.ExcelExport;
import com.neuswp.utils.VolEngineAI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AiQuestionCenterServiceImpl implements AiQuestionCenterService {

    @Autowired
    private AiChatHistoryMapper aiChatHistoryMapper;

    @Autowired
    private EasStudentMapper easStudentMapper;

    @Autowired
    private EasUserMapper easUserMapper;

    private VolEngineAI volEngineAI;

    @Autowired
    private ExcelExport excelExport;

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
    public String askQuestion(Integer userId, String question) {
        if (volEngineAI == null)
            return "未成功接入推理模型";

        /// 识别用户意图，进行带有功能调用的对话
        // 1. 意图解析 (学生用户暂不支持此功能)
        String parsedIntend = null;
        if (easStudentMapper.getStudentByUsername(easUserMapper.findUsernameById(userId)) == null)
            parsedIntend = parseUserInputToIntent(question);

        // 2. 功能调用
        String url = null;
        if (parsedIntend != null)
            url = excelExport.recognize(parsedIntend);

        // 3. 对话
        String reply = (url != null) ? "自动导出表格: " + url : volEngineAI.SimpleGenerate(question);

        // 4. 记录到历史对话表
        if (reply != null)
            aiChatHistoryMapper.save(userId, question, reply);

        return reply;
    }

    @Override
    public List<Map<String, Object>> getHistoryByUserId(Integer userId, Integer page, Integer limit) {

        // 1. 从数据库中读取对话记录
        List<Map<String, Object>> result = new ArrayList<>();

        // 2. 处理对话格式
        List<AiChatHistory> history = aiChatHistoryMapper.getHistoryByUserId(userId);
        for (AiChatHistory ele : history) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("question", ele.getQuestion());
            entry.put("answer", ele.getReply());
            result.add(entry);
        }

//        // t. 测试用对话记录
//        HashMap<String, Object> chat01 = new HashMap<>();
//        chat01.put("question", "Java中接口和抽象类的区别？");
//        chat01.put("answer", "- 接口中所有方法都是抽象的\n- 抽象类可以有具体实现的方法");
//        result.add(chat01);

        // 返回历史会话
        return result;
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
