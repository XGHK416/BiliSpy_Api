package com.project.xghk416.framework.shiro.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 自定义manager
 * 主要重写获取sessionid的方法
 */
public class CustomSessionManager extends DefaultWebSessionManager {

    /**
     * 获取指定sessionId
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest hrequest = WebUtils.toHttp(request);
        String id=null;
        //sessionId放在请求头里
//        id = hrequest.getHeader("X-Token");
        Cookie[] cookies =  hrequest.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    id = cookie.getValue();
                }
            }
        }


        if (StringUtils.isEmpty(id)){
            return super.getSessionId(request,response);
        }else {
            //id来源
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,"cookie");
            //id是啥
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,id);
            //id要不要验证
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
            return id;

        }
    }
}
