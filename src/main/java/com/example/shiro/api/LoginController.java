package com.example.shiro.api;

import com.example.shiro.mapper.UserMapper;
import com.example.shiro.response.ResponseInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginController {
    @Autowired
    private UserMapper mUserMapper;

    public static class TokenInfo {
        public String token;
        public int expiresIn;
        public String uid;
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseInfo<TokenInfo> login(
            @RequestParam("username") String phone,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        ResponseInfo<TokenInfo> result = new ResponseInfo<TokenInfo>();
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
            subject.login(token);
            String authorization = (String) subject.getSession().getId();
            result.setData(null); //将authorization传给前端，用于MySessionManager中请求的验证
        } catch (IncorrectCredentialsException e) {
            result.setMessage("用户名或密码错误");
        } catch (LockedAccountException e) {
            result.setMessage("该用户已被禁用");
        } catch (AuthenticationException e) {
            result.setMessage("用户名或密码错误");
        }
        return result;
    }
}
