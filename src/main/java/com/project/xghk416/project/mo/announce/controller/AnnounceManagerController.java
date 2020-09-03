package com.project.xghk416.project.mo.announce.controller;


import com.project.xghk416.enums.mo.ManagerOperationEnum;
import com.project.xghk416.enums.mo.TableEnum;
import com.project.xghk416.pojo.MoOperate;
import com.project.xghk416.pojo.announce.AnnouncePo;
import com.project.xghk416.common.result.Result;
import com.project.xghk416.project.mo.announce.service.impl.IAnnounceManagerService;
import com.project.xghk416.project.common.operate.service.impl.IMoLogService;
import com.project.xghk416.common.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@Api(tags = "公告管理")
@RequestMapping(value = "/announce")
public class AnnounceManagerController {
    @Autowired
    IAnnounceManagerService announceManagerService;
    @Autowired
    IMoLogService moLogService;

    @PostMapping(value = "/changeAnnounce")
    @ApiOperation(value = "上传新公告")
    public Result changeAnnounce(AnnouncePo announcePo){
        announcePo.setCreateTime(LocalDateTime.now());
        announcePo.setAnnounceId("announce"+LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        announceManagerService.addAnnounce(announcePo);
        MoOperate moOperate = new MoOperate(ManagerOperationEnum.CHANGE_ANNOUNCE, TableEnum.ANNOUNCE,announcePo.getCreateMoId(),"AnnouncePo",null,"AnnouncePo");
        moLogService.addOpera(moOperate);
        return ResultUtil.success();
    }

    @GetMapping(value = "/getAnnounce")
    @ApiOperation(value = "获取新公告")
    public Result getAnnounce(){
        return ResultUtil.success(announceManagerService.getCurrentAnnounce());
    }

}
