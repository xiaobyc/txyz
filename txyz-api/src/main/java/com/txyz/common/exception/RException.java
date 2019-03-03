package com.txyz.common.exception;

/**
 */
public class RException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String retCode;
    private String retDesc="000500";

    public RException(String msg) {
        super(msg);
        this.retCode="000500";
        this.retDesc = msg;
    }

    public RException(String msg, Throwable e) {
        super(msg, e);
        this.retDesc = msg;
    }

    public RException(String msg, String code) {
        super(msg);
        this.retDesc = msg;
        this.retCode = code;
    }

    public RException(String msg, String code, Throwable e) {
        super(msg, e);
        this.retDesc = msg;
        this.retCode = code;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }
}
