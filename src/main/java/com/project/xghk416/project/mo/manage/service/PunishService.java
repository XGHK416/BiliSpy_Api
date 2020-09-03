package com.project.xghk416.project.mo.manage.service;

import com.project.xghk416.pojo.punish.UserColdPo;

public interface PunishService {
    boolean coldViewer(UserColdPo userColdPo);

    boolean decoldViewer(String viewerId);

    boolean isColding(String viewerId);

    boolean writtenOffUser(String writtenOffId,boolean isWritten);
}
