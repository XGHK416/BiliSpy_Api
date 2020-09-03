package com.project.xghk416.framework.shiro.realm;

import com.project.xghk416.enums.UserStateEnum;
import com.project.xghk416.framework.shiro.pojo.UserDto;
import com.project.xghk416.framework.shiro.service.impl.IUserService;
import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.project.common.login.service.impl.ILoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    ILoginService loginService;
    @Autowired
    IUserService userService;

    @Override
    public void setName(String name) {
        super.setName("UserRealm");
    }

    /**
     * 进行授权
     * 先进性认证，在进行授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserDto user = (UserDto)principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = userService.getRole(user.getRole());
        Set<String> prem = userService.getPermission(user.getRole(),user.getIsCode(),user.getUsable());

        info.setStringPermissions(prem);
        info.setRoles(roles);
        return info;
    }

    /**
     * 认证方法
     *
     * @param authenticationToken 改参数即为控制层传入的UserPasswordToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("认证中。。。");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        System.out.println("用户："+username+"--密码:"+password);

        UserDto user = loginService.CheckLogin(username, password, "ORIGIN");

        if (user.getCode().equals(UserStateEnum.OPERATE_SUCCESS.getCode())){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password,this.getName());
            System.out.println("认证完成:成功");
            return info;
        }
        System.out.println("认证完成:失败");
        return null;
    }
}
