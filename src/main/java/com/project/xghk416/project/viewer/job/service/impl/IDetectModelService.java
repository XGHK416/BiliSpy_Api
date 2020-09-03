package com.project.xghk416.project.viewer.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.xghk416.pojo.detect.mapper.DetectUploaderStateDao;
import com.project.xghk416.pojo.detect.mapper.DetectVideoResultDao;
import com.project.xghk416.pojo.detect.mapper.DetectVideoStateDao;
import com.project.xghk416.pojo.detect.DetectUploaderStatePo;
import com.project.xghk416.pojo.detect.DetectVideoResultPo;
import com.project.xghk416.pojo.detect.DetectVideoStatePo;
import com.project.xghk416.project.viewer.job.service.DetectModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IDetectModelService")
public class IDetectModelService implements DetectModelService {
    @Autowired
    DetectVideoResultDao videoResultDao;
    @Autowired
    DetectVideoStateDao videoStateDao;
    @Autowired
    DetectUploaderStateDao uploaderStateDao;

    @Override
    public boolean addVideoJob(DetectVideoStatePo jobInfo) {
        return videoStateDao.insert(jobInfo)>0;
    }

    @Override
    public boolean addUploaderJob(DetectUploaderStatePo jobInfo) {
        return uploaderStateDao.insert(jobInfo)>0;
    }

    /**
     * 获取侦测结果
     * @return
     */
    @Override
    public List<DetectVideoResultPo> getResult(String detectId) {
        QueryWrapper<DetectVideoResultPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_detect_id",detectId);
        return videoResultDao.selectList(queryWrapper);
    }

    /**
     * 获取up主侦测列表
     * @param page
     * @param pageSize
     * @param createId
     * @return
     */
    @Override
    public List<DetectUploaderStatePo> getUploaderDetectList(Integer page,Integer pageSize,String createId,int type,String detectName) {
        Page<DetectUploaderStatePo> page1 = new Page<>(page,pageSize);
        page1.addOrder(OrderItem.desc("create_time"));
        List<DetectUploaderStatePo> result;
        switch (type){
            case 0:{
                result = uploaderStateDao.queryDetectUploaderList(page1,createId,detectName).getRecords();
                break;
            }
            case 1:{
                result = uploaderStateDao.queryDetectUploaderListUnstart(page1,createId,detectName).getRecords();
                break;
            }
            case 2:{
                result = uploaderStateDao.queryDetectUploaderListSatrting(page1,createId,detectName).getRecords();
                break;
            }
            case 3:{
                result = uploaderStateDao.queryDetectUploaderListFinish(page1,createId,detectName).getRecords();
                break;
            }
            default:return null;
        }
        return result;
    }

    /**
     * 获取视频侦测列表
     * @param page
     * @param pageSize
     * @param createId
     * @return
     */
    @Override
    public List<DetectVideoStatePo> getVideoDetectList(Integer page,Integer pageSize,String createId,int type,String parentId) {
        Page<DetectVideoStatePo> page1 = new Page<>(page,pageSize);
        page1.addOrder(OrderItem.desc("create_time"));
        List<DetectVideoStatePo> result;
        switch (type){
            case 0:{
                result = videoStateDao.queryDetectVideoList(page1,createId,parentId).getRecords();
                break;
            }
            case 1:{
                result = videoStateDao.queryDetectVideoListUnstart(page1,createId,parentId).getRecords();
                break;
            }
            case 2:{
                result = videoStateDao.queryDetectVideoListSatrting(page1,createId,parentId).getRecords();
                break;
            }
            case 3:{
                result = videoStateDao.queryDetectVideoListFinish(page1,createId,parentId).getRecords();
                break;
            }
            default:return null;
        }
        return result;
    }

    /**
     * 重新设置某一视频侦测
     * @param detectVideoStatePo
     * @return
     */
    @Override
    public boolean setVideoDetect(DetectVideoStatePo detectVideoStatePo) {
        UpdateWrapper<DetectVideoStatePo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("detect_id",detectVideoStatePo.getDetectId());

        return videoStateDao.update(detectVideoStatePo,updateWrapper)>0;
    }

    /**
     * 重新设置某一up主侦测
     * @param
     * @return
     */
    @Override
    public boolean setUploaderDetect(DetectUploaderStatePo detectUploaderStatePo,String group) {
        UpdateWrapper<DetectUploaderStatePo> uploaderStatePoUpdateWrapper = new UpdateWrapper<>();
        uploaderStatePoUpdateWrapper.eq("detect_id",group);

        return uploaderStateDao.update(detectUploaderStatePo,uploaderStatePoUpdateWrapper)>0;
    }

    /**
     * 添加侦测结果
     * @param resultPo
     * @return
     */
    @Override
    public boolean addVideoResult(DetectVideoResultPo resultPo) {
        return videoResultDao.insert(resultPo)>0;
    }

    /**
     * 获取总数
     * @param userId
     * @param type
     * @return
     */
    @Override
    public int getVideoDetectCount(String userId, int type,String parentId) {
        int num = 0;
        switch (type){
            case 0:{
                num = videoStateDao.queryDetectVideoCount(userId,parentId);
                break;
            }
            case 1:{
                num = videoStateDao.queryDetectVideoCountUnstart(userId,parentId);
                break;
            }
            case 2:{
                num = videoStateDao.queryDetectVideoCountSatrting(userId,parentId);
                break;
            }
            case 3:{
                num = videoStateDao.queryDetectVideoCountFinish(userId,parentId);
                break;
            }
            default:return num;
        }
        return num;
    }

    @Override
    public DetectVideoStatePo getVideoJob(String detectId) {
        QueryWrapper<DetectVideoStatePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("detect_id",detectId);

        return videoStateDao.selectOne(queryWrapper);
    }
    @Override
    public DetectUploaderStatePo getUploaderJob(String detectId) {
        QueryWrapper<DetectUploaderStatePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("detect_id",detectId);

        return uploaderStateDao.selectOne(queryWrapper);
    }

    @Override
    public int getUploaderDetectCount(String userId, int type,String detectName) {
        int num = 0;
        switch (type){
            case 0:{
                num = uploaderStateDao.queryDetectUploaderCount(userId,detectName);
                break;
            }
            case 1:{
                num = uploaderStateDao.queryDetectUploaderCountUnstart(userId,detectName);
                break;
            }
            case 2:{
                num = uploaderStateDao.queryDetectUploaderCountSatrting(userId,detectName);
                break;
            }
            case 3:{
                num = uploaderStateDao.queryDetectUploaderCountFinish(userId,detectName);
                break;
            }
            default:return num;
        }
        return num;
    }
}
