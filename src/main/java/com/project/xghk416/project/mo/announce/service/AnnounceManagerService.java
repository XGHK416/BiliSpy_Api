package com.project.xghk416.project.mo.announce.service;


import com.project.xghk416.pojo.announce.AnnouncePo;


public interface AnnounceManagerService {
    boolean addAnnounce(AnnouncePo announcePo);

    AnnouncePo getCurrentAnnounce();

}
