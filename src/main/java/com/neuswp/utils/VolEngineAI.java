package com.neuswp.utils;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


// 请确保将 API Key 存储在环境变量 ARK_API_KEY 中
@Slf4j
public class VolEngineAI {
    private String apiKey;
    private String baseUrl;
    private ConnectionPool connectionPool;
    private Dispatcher dispatcher;
    private ArkService service;

    public VolEngineAI() throws RuntimeException {

        try {
            apiKey = StringUtils.trim(System.getenv("ARK_API_KEY"));  // 从环境变量中获取 API Key
            baseUrl = "https://ark.cn-beijing.volces.com/api/v3";
            connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
            dispatcher = new Dispatcher();
            service = ArkService.builder()
                    .dispatcher(dispatcher)
                    .connectionPool(connectionPool)
                    .baseUrl(baseUrl)
                    .apiKey(apiKey)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("[VolEngineAI] Build Error: \n", e);
        }
    }

    public String SimpleGenerate(String question){

        // 组合消息上下文
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是人工智能助手，请用自然语言回复问题。").build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(question).build();
        messages.add(systemMessage);
        messages.add(userMessage);

        // 配置模型
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("deepseek-v3-250324")    // 指定您创建的方舟推理接入点 ID（模型名称）
                .messages(messages)
                .build();

        // 发送请求，组合回复为一个字符串
        StringBuilder replyBuilder = new StringBuilder();
        service.createChatCompletion(chatCompletionRequest)
                .getChoices()
                .forEach(choice -> replyBuilder.append(choice.getMessage().getContent()));

        // 返回结果
//        System.out.println(replyBuilder);
        return replyBuilder.toString();
    }
}