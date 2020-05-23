package com.example.paintingshop.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{

    DEMAND_NOT_FOUND(2001,"您找的需求不在了,换一个试试吧~"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何需求或评论进行回复。"),
    NO_LOGIN(2003,"请先登录~"),
    SYS_ERROR(2004,"问题2004，请排查。"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在。"),
    COMMENT_NOT_FOUND(2006,"您回复的评论不在了,换一个试试吧~"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空。"),
    REPEAT_ENLIST(2008,"您已经应征过这个企划了，耐心等待吧。"),
    FILE_UPLOAD_FAIL(2009,"图片上传失败。"),
    USER_NOT_FOUND(2010,"找不到这个用户,他可能人间蒸发了~"),
    USER_NOT_PAINTER(2011,"您不是画师哦，请先进行画师验证。"),
    REPEAT_LOGIN(2012,"您已经登陆了一个账号，不能重复登录，除非先退出。"),
    REPEAT_APPLY(2013,"正在审核中，请不要重复申请。"),
    ENLIST_NOT_FOUND(2014,"你找的应征信息不在了，换一个试试吧~"),
    ERROR_IDENTITY(2015,"走错房间啦，兄弟~"),
    PAY_FAIL(2016,"验签失败了，此交易存在风险~"),
    CHOICE_ENLIST_FAIL_DEMAND(2017,"该企划状态不允许选定应征！"),
    CHOICE_ENLIST_FAIL_ENLIST(2018,"该应征状态不允许被选定！"),
    GIVE_UP_ENLIST(2019,"已经放弃的企划不可再次接受！"),
    FOLLOW_NOT_FOUND(2020,"找不到关注数据！"),
    TYPE_NOT_FOUND(2021,"错误，没有该检索项"),
    NO_QUALIFICATION(2022,"抱歉，您不满足以上所有条件"),
    REPEAT_NOTIFICATION(2023,"已经通知对方，请等待"),
    TOO_MORE(2024,"你生成了太多未支付订单啦，不能再开始新的了,请先处理其他未支付订单"),
    ERROR_CODE(2025,"错误的请求码"),


    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
