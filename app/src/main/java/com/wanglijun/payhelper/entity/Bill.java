package com.wanglijun.payhelper.entity;


/**
 * 监听到通知的时候生成一个账单,先保存在本地数据库,然后上传至服务器,如果成功会打上标记
 */
public class Bill {


    public static final String TABLE_NAME = "bill";
    public static final String _ID = "id";
    public static final String DATE = "create_date";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String MONEY = "money";
    public static final String SYNC = "sync";
    public static final String UID = "uid";


    private long id;
    private long user_id;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    private String date;
    private String title;
    private String content;
    private String money;
    private int sync;


    public Bill() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getSync() {
        return this.sync;
    }
}
