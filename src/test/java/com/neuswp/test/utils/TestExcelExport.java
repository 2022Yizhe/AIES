package com.neuswp.test.utils;


import com.neuswp.utils.ExcelExport;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestExcelExport {

    @Autowired
    ExcelExport excelExport;

    @Test
    public void testRecognize(){
        String issue;
        String result;

        issue = "导出课程表";
        System.out.println("[Test] testRecognize: " + issue);
        result = excelExport.recognize(issue);
        Assert.assertNotNull(result);   // 断言结果不为空
        System.out.println("[Test] testRecognize: " + result);
        System.out.println("[Test] testRecognize: passed!");

        issue = "导出班级表";
        System.out.println("[Test] testRecognize: " + issue);
        result = excelExport.recognize(issue);
        Assert.assertNotNull(result);   // 断言结果不为空
        System.out.println("[Test] testRecognize: " + result);
        System.out.println("[Test] testRecognize: passed!");

        issue = "导出教师表";
        System.out.println("[Test] testRecognize: " + issue);
        result = excelExport.recognize(issue);
        Assert.assertNotNull(result);   // 断言结果不为空
        System.out.println("[Test] testRecognize: " + result);
        System.out.println("[Test] testRecognize: passed!");

        issue = "导出学生表";
        System.out.println("[Test] testRecognize: " + issue);
        result = excelExport.recognize(issue);
        Assert.assertNotNull(result);   // 断言结果不为空
        System.out.println("[Test] testRecognize: " + result);
        System.out.println("[Test] testRecognize: passed!");
    }
}
