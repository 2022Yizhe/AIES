package com.neuswp.controller;

import com.neuswp.entity.EasUser;
import com.neuswp.entity.Result;
import com.neuswp.services.AiQuestionCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
@RequestMapping("/aiQuestionCenter")
public class AiQuestionCenterController {

    @Autowired
    private AiQuestionCenterService aiQuestionService;

    /**
     * 跳转到AI问答中心页面
     */
    @RequestMapping("/index")
    public String index() {
        return "system/aiQuestionCenter/index";
    }

    /**
     * 提交问题给 AI 并获取回答
     */
    @RequestMapping(value = "/ask", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> askQuestion(@RequestParam String question, HttpSession session) {

        try {
            // 1. 获取当前用户
            EasUser currentUser = (EasUser) session.getAttribute("login_user");
            System.out.println("[Controller] Current User: " + currentUser);

            // 2. 调用 AI 服务获取回答
            String aiMessage = aiQuestionService.simpleAskQuestion(question);
            return Result.success(aiMessage);

        } catch (Exception e) {
            return Result.error("获取 AI 回答失败: " + e.getMessage());
        }
    }
}