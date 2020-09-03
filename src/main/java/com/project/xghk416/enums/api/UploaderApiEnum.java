package com.project.xghk416.enums.api;

public enum UploaderApiEnum {

    UPLOADER_UPSTAT_RANK("u_api1","https://api.bilibili.com/x/space/upstat?"),
    UPLOADER_STAT("u_api2","https://api.bilibili.com/x/relation/stat?"),
    UPLOADER_INFO("u_api3","https://api.bilibili.com/x/space/acc/info?"),
    UPLOADER_NOTICE("u_api4","https://api.bilibili.com/x/space/notice?"),
    UPLOADER_SEARCH("u_api5","https://api.bilibili.com/x/web-interface/search/type?search_type=bili_user&"),
    UPLOADER_VIDEO_PUBLISH("u_api6","https://api.bilibili.com/x/space/arc/search?ps=10&tid=0&pn=1&order=pubdate&");



    UploaderApiEnum(String apiId, String apiAddress) {
        ApiId = apiId;
        ApiAddress = apiAddress;
    }

    private String ApiId;
    private String ApiAddress;

    public static String getAddressById(String apiId){
        UploaderApiEnum[] apis = values();
        for (UploaderApiEnum item:
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
