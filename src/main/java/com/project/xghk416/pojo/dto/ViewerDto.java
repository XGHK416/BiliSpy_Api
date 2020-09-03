package com.project.xghk416.pojo.dto;

import com.project.xghk416.pojo.user.UserAccountPo;
import com.project.xghk416.pojo.operate.UserLogOperaPo;
import lombok.Data;

import java.util.List;

@Data
public class ViewerDto {
    private UserAccountPo accountPo;
    private List<UserLogOperaPo> userOpera;
    private List<UserAccountPo> accountList;

    public ViewerDto(UserAccountPo accountPo,List<UserLogOperaPo> userOpera) {
        this.accountPo = accountPo;
        this.userOpera = userOpera;
    }

    public ViewerDto(List<UserAccountPo>accountList) {
        this.accountList = accountList;
    }

}
