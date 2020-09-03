package com.project.xghk416.enums;

public enum VideoTidEnum {
    //这里是可以自己定义的，方便与前端交互即可
    ANIMATION(1,"动画",0),
    BANGUMI(13,"番剧",0),
    DOMESTIC(168,"国创相关",0),
    MUSIC(3,"音乐",0),
    DANCE(129,"舞蹈",0),
    GAME(4,"游戏",0),
    TECHOLOGY(36,"科技",0),
    LIFE(160,"生活",0),
    DIGITAL(188,"数码",0),
    KICHIKUKI(119,"鬼畜",0),
    FASHION(155,"时尚",0),
    ENTERTAIMENT(5,"娱乐",0),
    TELEVISION(181,"影视",0),
    ADVERTISING(165,"广告",0),
    SCREENS(200,"放映厅",0);

    private Integer tid;
    private String name;
    private Integer parent;

    VideoTidEnum(Integer tid, String name, Integer parent) {
        this.tid = tid;
        this.name = name;
        this.parent = parent;

    }

    public String getName() {
        return name;
    }

    public Integer getTid() {
        return tid;
    }

    public Integer getParent() {
        return parent;
    }
}
