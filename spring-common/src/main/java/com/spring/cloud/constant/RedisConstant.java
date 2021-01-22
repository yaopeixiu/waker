package com.spring.cloud.constant;

public class RedisConstant {

    public static String LOGIN = "exception:revoke:order";


    /**
     * 用户ID ----》token
     */
    public static final String USER_TOKEN = "user_token";

    /**
     * token----》用户ID
     */
    public static final String TOKEN_USER = "token_user";

    /**
     * token----》用户JSON
     */
    public static final String TOKEN_USER_JSON = "token_user_json";


    /**
     * 验证码
     */
    public static final String SMS = "yzm_";


    /**
     * 每日交易总共手续费
     */
    public static final String TOTAL_FEE = "total_fee_";


    /**
     * 网关配置信息
     */
    public static final String GATEWAY_CONFIG = "gateway_config";


    /**
     * ip黑名单
     */
    public static final String BLACKLIST = "blacklist";

    /**
     * 当日请求次数
     */
    public static final String TOD_REQ_COUNT = "_req_count";

    /**
     * 分享值小于150的只允许转一次，且转账金额小于等于3
     */
    public static final String SHARE_TRANSFER = "share_transfer";


    /**
     * 转赠开关，0或者不存在则不支持转赠，1开通转赠
     */
    public static final String TRANSFER_STATUS = "transfer_status";


    /**
     * 当日平台转赠次数
     */
    public static final String TRANSFER_COUNT = "transfer_count_";

    /**
     * 当日平台转赠总额
     */
    public static final String TRANSFER_TOTLE = "transfer_totle_";
}
