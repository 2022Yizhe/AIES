package com.neuswp.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 统一响应结果类
 * REST-ful
 * @param <T> 响应数据类型
 */
@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    /**
     * 重新包装响应结果（前端并不支持 REST 响应结果）
     * 因此只推荐响应数据有且仅有 data 的情况
     * @param result REST 响应结果
     * @return 响应结果
     */
    private static <T> Map<String, Object> genMapResult(Result<T> result){
        Map<String, Object> map = new HashMap<>();
        map.put("code", result.getCode());
        map.put("msg", result.getMessage());
        map.put("data", result.getData());
        return map;
    }

    public static <T> Map<String, Object> success(){
        Result<T> result = new Result<>();
        result.code = 0;
        result.message = "success";
        return Result.genMapResult(result);
    }

    public static <T> Map<String, Object> success(T data){
        Result<T> result = new Result<>();
        result.code = 0;
        result.message = "success";
        result.data = data;
        return Result.genMapResult(result);
    }

    public static <T> Map<String, Object> error(){
        Result<T> result = new Result<>();
        result.code = 1;
        result.message = "Unknown Error Occurred";
        return Result.genMapResult(result);
    }

    public static <T> Map<String, Object> error(String message){
        Result<T> result = new Result<>();
        result.code = 1;
        result.message = message;
        return Result.genMapResult(result);
    }
}
