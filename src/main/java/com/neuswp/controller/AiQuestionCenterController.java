package com.neuswp.controller;

import com.neuswp.entity.EasUser;
import com.neuswp.entity.Result;
import com.neuswp.services.AiQuestionCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/aiQuestionCenter")
public class AiQuestionCenterController {

    @Autowired
    private AiQuestionCenterService aiQuestionService;

    /**
     * 跳转到 AI 问答中心页面
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

        // 1. 获取当前用户
        EasUser currentUser = (EasUser) session.getAttribute("login_user");
        System.out.println("[Controller] Current User: " + currentUser);

        // 2. 调用 AI 服务获取回答
//        String aiMessage = aiQuestionService.simpleAskQuestion(question);
        String aiMessage = aiQuestionService.askQuestion(question);
        return Result.success(aiMessage);
    }


    /**
     * 从内存中读取历史会话记录并返回
     */
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ResponseBody // 注意添加 @ResponseBody 注解，表示返回的是 JSON 数据
    public Map<String, Object> getHistory(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            HttpSession session) {

        // 1. 获取当前用户（用于查询其历史对话）
        EasUser currentUser = (EasUser) session.getAttribute("login_user");
        if (currentUser == null) {
            return Result.error("用户未登录");
        }

        // 2. 调用 service 获取历史记录（假设根据用户ID查询）
        List<Map<String, Object>> historyList = aiQuestionService.getHistoryByUserId(currentUser.getId(), page, limit);

        // 3. 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("msg", "success");

        // 假设 service 返回的是当前页的数据列表
        result.put("data", historyList);

        return result;
    }

}