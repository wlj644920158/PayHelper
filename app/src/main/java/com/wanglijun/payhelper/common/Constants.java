package com.wanglijun.payhelper.common;

public class Constants {


    public static final boolean IS_DEBUG = true;


    //这里的被监听的报名被我和谐了
    public static final String LISTENING_TARGET_PKG = "com.xxx.xpay";
    //这里post的地址被我和谐了
    public static final String BASE_URL = "http://xxx.xxx.xxx.xxx/wap/";
    public static final int DEFAULT_TIMEOUT = 30;
    public static final String SP_NAME = "com.wanglijun.payhelper.SP_NAME";
    public static final String SP_USER_ID = "SP_USER_ID";


    public static final String DB_NAME = "helper.db";
    public static final int DATABASE_VERSION = 1;


    public static final String SERVICE_ACTION = "com.wanglijun.payhelper.LOGIN_ACTION";


    public static final int BROADCAST_TYPE_CONNECTED_SERVICE = 1;
    public static final int BROADCAST_TYPE_DISCONNECTED_SERVICE = 2;
    public static final int BROADCAST_TYPE_NEW_BILL = 3;

}
