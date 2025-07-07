package com.neuswp.test.controller;


import com.neuswp.controller.EasBaseCourseController;
import com.neuswp.entity.EasBaseCourse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestEasBaseCourse {

    @Autowired
    private EasBaseCourseController easBaseCourseController; // 由 Spring 自动注入

    /// Controller 单元测试示例
    @Test
    public void testList() throws Exception {
        // 调用 list 方法，模拟 page=1, limit=10, easBaseCourse=null
        Map<String, Object> result = easBaseCourseController.list(1, 10, new EasBaseCourse());

        // 验证返回值是否包含预期 key (断言错误则直接退出测试)
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.get("code"));
        Assert.assertTrue(result.containsKey("data"));
        Assert.assertTrue(result.containsKey("count"));

        System.out.println("[Test] testList: passed!");
    }
}
