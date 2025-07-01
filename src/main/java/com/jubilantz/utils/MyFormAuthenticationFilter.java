package com.jubilantz.utils;

import com.jubilantz.controller.Constants;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * 在用户名密码验证前先检查验证码
 * 注：这个过滤器本身不直接校验密码，密码校验是由其父类 FormAuthenticationFilter 完成的
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        System.out.println("开始验证码校验...");

        // 从 session 中获取正确验证码
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();

        // 取出 session 中的验证码
        String validateCode = (String) session.getAttribute(Constants.VALIDATE_CODE);
//        System.out.println("我是正确验证码：" + validateCode);

        // 取出页面的验证码
        String randomCode = httpServletRequest.getParameter(Constants.RANDOM_CODE);
//        System.out.println("我是输入验证码：" + randomCode);

        // 验证码校验，如果失败则将验证码错误信息通过 shiroLoginFailure 写入 request
        if(randomCode != null && validateCode !=null && !randomCode.equals(validateCode)){
            httpServletRequest.setAttribute("shiroLoginFailure",Constants.CODE_ERROR);
            return true;    // 拒绝访问，不校验用户名密码
        }

        // 密码认证通过调用父类的认证方法，父类会调用 Shiro 的 Subject.login 方法进行认证，实际的密码校验是由 Shiro 的 Realm 实现完成的
        return super.onAccessDenied(request, response);
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
//        System.out.println("这是哪");
        WebUtils.issueRedirect(request, response, getSuccessUrl(),null,true);
    }
}
