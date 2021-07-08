package com.javaclient.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Blue_Sky 7/8/21
 */
public class JsonUtil {

    // define jackson object
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Convert the object into a json string.
     *  @return
     */
    public static String objectToJson( Object object){
        try {
            String string = OBJECT_MAPPER.writeValueAsString(object);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the json result set into an object
     *
     * @param jsonData json data
     * @param class object type in the object
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType){
        try {
            T t = OBJECT_MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
