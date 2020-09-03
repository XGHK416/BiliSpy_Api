package com.project.xghk416.framework.shiro.pojo;

import com.project.xghk416.pojo.token.UserTokenPo;
import lombok.Data;
import org.apache.catalina.User;
import org.crazycake.shiro.AuthCachePrincipal;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class UserDto implements Serializable,AuthCachePrincipal {

    private UserTokenPo token;
    private String sessionId;
    private String user_id;
    private String status;
    private int isCode;
    private String usable;
    private Integer code;
    private String role;


    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
