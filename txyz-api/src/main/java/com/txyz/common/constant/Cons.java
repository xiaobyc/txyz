package com.txyz.common.constant;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 */
public interface Cons {
    String JSON_UTF8="application/json;charset=utf-8";
    SerializerFeature[] JSON_FEATURES={SerializerFeature.WriteMapNullValue,
            SerializerFeature.QuoteFieldNames,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullListAsEmpty
    };
}
