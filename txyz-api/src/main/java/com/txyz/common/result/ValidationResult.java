package com.txyz.common.result;

/**
 * 校验异常返回
 */
public class ValidationResult {
    private String retCode;
    private String retDesc;

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
