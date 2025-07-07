package com.neuswp.test.sercive;

import com.neuswp.services.AiQuestionCenterService;
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
    public void testSimpleAskQuestion() {
        String question = "今天天气如何";
        String answer = aiQuestionCenterService.simpleAskQuestion(question);

        // 验证返回值是否为 null (断言错误则直接退出测试)
        Assert.assertNotNull(answer);

        System.out.println("[Test] testSimpleAskQuestion: passed!");
    }
}
