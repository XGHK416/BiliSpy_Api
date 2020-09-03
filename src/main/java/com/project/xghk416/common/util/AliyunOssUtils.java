package com.project.xghk416.common.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.project.xghk416.framework.config.AliyunOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;

@Component
public class AliyunOssUtils {
    @Autowired
    AliyunOssProperties aliyunOssProperties;

    /** 上传文件*/
    public  String upLoad(File file,String userId){

        String endpoint = aliyunOssProperties.getEndpoint();
//        System.out.println("获取到的Point为:"+endpoint);
        String accessKeyId = aliyunOssProperties.getAccessId();
//        System.out.println("获取到的AccessId为:"+accessKeyId);
        String accessKeySecret = aliyunOssProperties.getAccessKey();
//        System.out.println("获取到的AccessKey为:"+accessKeySecret);
        String bucketName = aliyunOssProperties.getBucket();
//        System.out.println("获取到的Bucket为:"+bucketName);
        String fileHost = aliyunOssProperties.getDir();
//        System.out.println("获取到的fileHost为:"+fileHost);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        int i = (int)(Math.random()*50+100000);
        // 判断文件
        if(file==null){
            return null;
        }
        String fileUrl = "";
        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称

            fileUrl = fileHost + userId+(file.getName().substring(file.getName().lastIndexOf("."),file.getName().length()));
//            System.out.println("fileUrl="+fileUrl);
            // 上传文件
            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
//                System.out.println("Result"+fileUrl);
            }

        }catch (OSSException oe){
            System.out.println("errorOSSException"+oe.getMessage());
        }catch (ClientException ce){
            System.out.println("errorClientException"+ce.getMessage());
        }finally{
            if(client!=null){
                client.shutdown();
            }
        }
        return "https://"+bucketName+"."+endpoint+"/"+fileUrl;
    }
}
