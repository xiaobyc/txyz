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
    //参数验证错误
    String PARAM_ERROR="001001";
}
