package com.project.xghk416.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.xghk416.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class RequestTemplateUtil {
    private static RestTemplate restTemplate = new RestTemplate();


    public static Map<String,Object> requestGet(String url){
        Map<String,Object> result = new HashMap<>();
        String regexp = "\'";
        ResponseEntity<String> resultEntity = restTemplate.exchange(url, HttpMethod.GET,null,String.class);
        String json = Objects.requireNonNull(resultEntity.getBody()).replaceAll(regexp, "\'");
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(json,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("code", ResultEnum.SUCCESS.getCode());
        result.put("msg", ResultEnum.SUCCESS.getMsg());
        return result;

    }
    public static Map<String,Object> requestPost(String url, MultiValueMap<String, String> paramMap){
        Map<String,Object> result = new HashMap<>();
        String regexp = "\'";
        System.out.println("url:"+url+paramMap.toString());
        String resultEntity = restTemplate.postForObject(url, paramMap,String.class);
        System.out.println(resultEntity);
        String json = Objects.requireNonNull(resultEntity).replaceAll(regexp, "\'");
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(json,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("code", ResultEnum.SUCCESS.getCode());
        result.put("msg", ResultEnum.SUCCESS.getMsg());
        return result;

    }

    public static JSONObject requestGetForJson(String url){
        Map<String,Object> result = new HashMap<>();
        String regexp = "\'";
        ResponseEntity<String> resultEntity = restTemplate.exchange(url, HttpMethod.GET,null,String.class);
        String json = resultEntity.getBody();
        return JSONArray.parseObject(json);

    }
}
