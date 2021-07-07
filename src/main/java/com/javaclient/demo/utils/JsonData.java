package com.javaclient.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonData {

    // setter and getter is in the @Data Annotation
    private Integer code;

    private Object data;

    private String msg;

    public static JsonData buildSuccess(){
        return new JsonData(0,null,null);
    }

    public static JsonData buildSuccess(Object obj){
        return new JsonData(0,obj,null);
    }

    public static JsonData buildError(String msg){
        return new JsonData(-1,null, msg);
    }

}
