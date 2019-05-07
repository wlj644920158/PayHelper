package com.wanglijun.payhelper.entity;

public class Result <T> {

    private int status;
    private String result;
    private String msg;
    private T params;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
