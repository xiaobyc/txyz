package com.txyz.common.result;



import com.txyz.common.constant.ResponseCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 2018/11/16.
 */
public class R extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("retCode", ResponseCode.SUCCESS);
    }

    public static R error() {
        return error("000500", "未知异常，请联系管理员");
    }


    public static R noData() {
        R r = new R();
        r.put("retCode", ResponseCode.NODATA);
        r.put("retDesc", "没有数据返回");
        return r;
    }
    public static R error(String msg) {
        return error("000500", msg);
    }

    public static R error(String code, String msg) {
        R r = new R();
        r.put("retCode", code);
        r.put("retDesc", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("retCode",ResponseCode.SUCCESS);
        r.put("retDesc", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        R r = new R();
        r.put("retCode",ResponseCode.SUCCESS);
        r.put("retDesc", "执行成功");
        return r;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
