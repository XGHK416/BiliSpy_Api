package com.project.xghk416.enums.api;

public enum VideoApiEnum {
    VIDEO_RANK("v_api1","https://api.bilibili.com/x/web-interface/ranking?type=1&arc_type=0&jsonp=jsonp&"),
    VIDEO_INFO("v_api2","https://api.bilibili.com/x/web-interface/view?"),
    VIDEO_INFO_BVID("v_api3","https://api.bilibili.com/x/web-interface/view?"),
    VIDEO_LIST("v_api4","https://api.bilibili.com/x/web-interface/search/type?search_type=video&");

    VideoApiEnum(String apiId, String apiAddress) {
        ApiId = apiId;
        ApiAddress = apiAddress;
    }

    private String ApiId;
    private String ApiAddress;

    public static String getAddressById(String apiId){
        VideoApiEnum[] apis = values();
        for (VideoApiEnum item:
                apis) {
            if (apiId.equals(item.getApiId())){
                return item.getApiAddress();
            }
        }
        return null;
    }

    public String getApiId() {
        return ApiId;
    }

    public String getApiAddress() {
        return ApiAddress;
    }
}
