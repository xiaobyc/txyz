package com.txyz.common.constant;

/**
 * 返回码常量定义
 */
public interface ResponseCode {
    /** 未授权*/
    String UNAUTHORIZED="000011";
    /** 成功 */
    String SUCCESS="000000";
    /** 失败 */
    String ERROR="000001";
    /**没有数据返回**/
    String NODATA="000003";
    /**账号不存在**/
    String ACCOUNT_NOT_EXIST="000004";
    /**密码错误**/
    String PASSWORD_ERROR="000005";
    //参数验证错误
    String PARAM_ERROR="100000";
    //异常状态码
    String EXCEPTION="000500";
    //token 过期
    String TOKEN_EXPIRE="200001";
    //token 必须传token
    String TOKEN_NEED="200000";
}
