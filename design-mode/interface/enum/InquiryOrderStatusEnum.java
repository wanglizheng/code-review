package com.inspur.health.inquiry.constant;

public enum InquiryOrderStatusEnum {

    NONE(0, "none","未知"),                 //其他
    REGISTER(100, "register","挂号"),       //待接诊
    ACCEPTED(200, "accepted","已接诊"),     //进行中
    CLOSING(207, "closing","待关闭"),       //进行中
    RefuseCLOSE(205, "RefuseCLOSE","拒绝关闭"), //进行中 暂不保存
    EVALUATING(209, "evaluating","待评价"),     //完成
    REJECTED(300, "rejected","已拒诊"),         //完成
    EVALUATED(400, "evaluated","已评价"),       //完成
    Cancelled(500, "cancelled", "已取消");      //完成


    private int value;
    private String manager;
    private String spec;

    InquiryOrderStatusEnum(int value, String manager, String spec) {
        this.value = value;
        this.manager = manager;
        this.spec = spec;
    }

    public int getValue() {
        return value;
    }

    public String getManager() {
        return manager;
    }

    public String getSpec() {
        return spec;
    }


    public static InquiryOrderStatusEnum getEnumByValue(int value) {
        switch (value) {
            case 100:
                return REGISTER;
            case 200:
                return ACCEPTED;
            case 205:
                return RefuseCLOSE;
            case 207:
                return CLOSING;
            case 209:
                return EVALUATING;
            case 300:
                return REJECTED;
            case 400:
                return EVALUATED;
            default:
                return NONE;
        }
    }

}
