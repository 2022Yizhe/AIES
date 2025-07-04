package com.neuswp.controller;

import com.neuswp.entity.EasUser;
import com.neuswp.services.AiQuestionCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/aiQuestionCenter")
public class AiQuestionCenterController {

    @Autowired
    private AiQuestionCenterService aiQuestionService;

//    @Autowired
//    private ConversationHistoryService historyService;

    /**
     * 跳转到AI问答中心页面
     */
    @RequestMapping("/index")
    public String index() {
        return "system/aiQuestionCenter/index";
    }

    /**
     * 提交问题给AI并获取回答
     */
    @RequestMapping(value = "/ask", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> askQuestion(@RequestParam String question, HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            // 1. 获取当前用户
            EasUser currentUser = (EasUser) session.getAttribute("login_user");
            System.out.println("[Controller] Current User: " + currentUser);

            // 2. 调用AI服务获取回答
//            AIResponse aiResponse = aiQuestionService.getAIAnswer(question, currentUser.getId());
            String aiMessage = aiQuestionService.simpleAskQuestion(question);

            // 3. 保存对话记录
//            historyService.saveConversation(
//                    currentUser.getId(),
//                    question,
//                    aiResponse.getAnswer(),
//                    new Date()
//            );

            // 4. 构造响应
            response.put("code", 0);
            response.put("msg", "success");
//            response.put("data", aiResponse);
            response.put("data", aiMessage);

        } catch (Exception e) {
            response.put("code", 1);
            response.put("msg", "获取AI回答失败: " + e.getMessage());
        }

        return response;
    }
}