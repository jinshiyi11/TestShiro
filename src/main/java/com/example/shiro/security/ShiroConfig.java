package com.example.shiro.security;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
//        chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");  //login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
//        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
//        chainDefinition.addPathDefinition("/image/**", "anon");
//        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]"); //只允许admin或manager角色的用户访问
//        chainDefinition.addPathDefinition("/article/list", "noSessionCreation,authcToken");
//        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authcToken[permissive]");
//        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken"); // 默认进行用户鉴权

        chainDefinition.addPathDefinition("/web/login.html", "anon");
        chainDefinition.addPathDefinition("/login", "anon");  //login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/logout", "authc"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
        chainDefinition.addPathDefinition("/image/**", "anon");
        chainDefinition.addPathDefinition("/admin/**", "authc"); //只允许admin或manager角色的用户访问
        chainDefinition.addPathDefinition("/article/list", "authc");
        chainDefinition.addPathDefinition("/article/*", "authc");
        chainDefinition.addPathDefinition("/**", "authc"); // 默认进行用户鉴权

        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/web/login.html");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinition.getFilterChainMap());
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        //securityManager.setSessionManager(sessionManager());
        //注入记住我管理器
        //securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public MyRealm myShiroRealm() {
        MyRealm myShiroRealm = new MyRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 密码凭证匹配器，作为自定义认证的基础
     *  （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
//    @Bean
//    protected SessionStorageEvaluator sessionStorageEvaluator(){
//        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
//        sessionStorageEvaluator.setSessionStorageEnabled(false);
//        return sessionStorageEvaluator;
//    }
}
