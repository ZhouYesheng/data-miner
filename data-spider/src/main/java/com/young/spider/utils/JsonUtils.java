package com.young.spider.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> tClass) throws IOException {
        return mapper.readValue(json, tClass);
    }

    public static final <T> T fromJson(String json,TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(json, typeReference);
    }
}
