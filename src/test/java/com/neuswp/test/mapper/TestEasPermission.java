package com.neuswp.test.mapper;

import com.neuswp.entity.EasPermission;
import com.neuswp.mappers.EasPermissionMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestEasPermission {
    @Autowired
    private EasPermissionMapper easPermissionMapper;

    @Test
    public void testGetPermission(){
        List<EasPermission> list = easPermissionMapper.getParentPers();

        // 验证返回值有效性 (断言错误则直接退出测试)
        if (list == null || list.isEmpty())
            Assert.fail("没有查询到数据");

//        for (EasPermission first :list) {
//            System.out.println(first.getText());
//            for (EasPermission second :first.getChildren()) {
//                System.out.println("\t"+second.getText());
//                for (EasPermission third :
//                        second.getChildren()) {
//                    System.out.println("\t\t"+third.getText());
//                }
//            }
//        }

        System.out.println("[Test] testGetPermission: passed!");
    }

    /**
     * 单独测试 加密算法
     */
    @Test
    public void MD5(){
        String password = "123456";//明码
        String algorithmName = "MD5";//加密算法
        Object source = password;//要加密的密码

        Object salt = "admin";//盐值，一般都是用户名或者userid，要保证唯一
        int hashIterations = 1;//加密次数

        SimpleHash simpleHash = new SimpleHash(algorithmName,source,salt,hashIterations);
        System.out.println("[Test] testSimpleHash: " + "simpleHash: " + simpleHash);

        System.out.println("[Test] testSimpleHash: passed!");
    }
}
