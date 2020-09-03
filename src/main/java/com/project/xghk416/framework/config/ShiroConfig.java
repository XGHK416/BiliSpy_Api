package com.project.xghk416.framework.config;

import com.project.xghk416.framework.shiro.realm.UserRealm;
import com.project.xghk416.framework.shiro.session.CustomSessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 配置shiro的过滤器工厂
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {

//        创造过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /** 过滤器的通用配置*/
        //没有登录时的跳转地址
        shiroFilterFactoryBean.setLoginUrl("/auths?code=-1");
        //当没有授权是的跳转地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/auths?code=1");

        /** 设置过滤器的集合
         *  使用LinkedHashMap来作为过滤气得设置
         *  key为地址
         *  vcalue为过滤器类型
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login","anon");//当前地址可以匿名访问
//        filterMap.put("/apiMo/**","authc");//当前地址需要认证访问

//        filterMap.put("/detectJob/getVideoJogList","perms[viewer:monitor]");
//        filterMap.put("/detectJob/getVideoJogList","roles[viewer]");
//        filterMap.put("/detectJob/getUploaderJobList","perms[viewer:monitor]");
//        filterMap.put("/detectJob/getUploaderJobList","roles[viewer]");



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

//        /**
//         * 凭证匹配器
//         * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
//         * @return
//         */
//        @Bean
//        public HashedCredentialsMatcher hashedCredentialsMatcher() {
//            HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//            hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//            hashedCredentialsMatcher.setHashIterations(3);//散列的次数，比如散列两次，相当于 md5(md5(md5("")));
//            return hashedCredentialsMatcher;
//        }

    /**
     * 注入自定义的Realm类
     **/
    @Bean
    public UserRealm userRealm() {
//            userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return new UserRealm();
    }


    /**
     * 创建安全管理器
     *
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(userRealm());

        //将自定义的会话管理器注册到安全管理器中
        securityManager.setSessionManager(sessionManager());
        //将自定义的redis缓存管理器主车道安全管理器中
        securityManager.setCacheManager(cacheManager());

        return securityManager;
    }

    /**
     * 开启注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /** 指定以Session管理配置*/
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;

    /**
     * redis的控制器，操作redis
     */
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);

        return redisManager;
    }


    /**
     * sessionDao
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    /**
     * 会话管理
     */
    public DefaultWebSessionManager sessionManager(){
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * 缓存管理器
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;

    }



}
