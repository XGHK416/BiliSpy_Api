package com.project.xghk416.project.mo.announce.service.impl;

import com.project.xghk416.pojo.announce.mapper.AnnounceDao;
import com.project.xghk416.pojo.announce.AnnouncePo;
import com.project.xghk416.project.mo.announce.service.AnnounceManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAnnounceManagerService implements AnnounceManagerService {

    @Autowired
    AnnounceDao announceDao;


    @Override
    public boolean addAnnounce(AnnouncePo announcePo) {
        return announceDao.insert(announcePo)>0;
    }

    @Override
    public AnnouncePo getCurrentAnnounce() {
        return announceDao.getCurrentAnnounce();
    }
}
