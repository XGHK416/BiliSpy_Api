package com.project.xghk416.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application-dev.yml")
@ConfigurationProperties("aliyun.oss")
public class AliyunOssProperties {

    private String accessId;
    private String accessKey;
    private String bucket;
    private String endpoint;
    private String dir;
    private Integer maxSize = 1;
    private Integer expire = 30;
    private boolean secure = false;
    private String roleSessionName;

    public AliyunOssProperties() {
        accessId = "";
        accessKey="";
        bucket = "xghk416";
        endpoint = "oss-cn-beijing.aliyuncs.com";
        dir = "BiliSpy/userProfile";
    }
}
