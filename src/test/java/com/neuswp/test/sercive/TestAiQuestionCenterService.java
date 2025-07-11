package com.neuswp.test.sercive;

import com.neuswp.services.AiQuestionCenterService;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestAiQuestionCenterService {

    @Autowired
    private AiQuestionCenterService aiQuestionCenterService;

    @Test
    public void testVolcEngineSdkEnv(){
        // 从环境变量中获取 API Key
        String apiKey = StringUtils.trim(System.getenv("ARK_API_KEY"));

        // 验证 API Key 是否存在
        Assert.assertNotNull(apiKey);

        System.out.println("[Test] testVolcEngineSdkEnv: Your API Key: " + apiKey);
        System.out.println("[Test] testVolcEngineSdkEnv: passed!");
    }

    @Test
    public void testSimpleAskQuestion() {
        String question = "今天天气如何";
        String answer = aiQuestionCenterService.simpleAskQuestion(question);

        // 验证返回值是否为 null (断言错误则直接退出测试)
        Assert.assertNotNull(answer);

        System.out.println("[Test] testSimpleAskQuestion: passed!");
    }
}
