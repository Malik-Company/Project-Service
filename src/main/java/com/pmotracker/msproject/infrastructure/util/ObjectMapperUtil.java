package com.pmotracker.msproject.infrastructure.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * @author Malik Imran
 * @Since 3/20/2020
 */

public class ObjectMapperUtil {

    private ObjectMapperUtil() {
    }

    public static JSONObject toJSONObject(String jsonString) {
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(jsonString).nextValue();
        } catch (JSONException e) {
            throw new RuntimeException("Error occurred while parsing json string to jsonObject:" + e.getMessage(), e);
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }

    public static Map toMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (!Optional.ofNullable(obj).isPresent()) {
            return map;
        }
        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, Boolean.FALSE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            if (obj instanceof String)
                jsonString = (String) obj;
            else
                jsonString = toJSONObject(mapper.writeValueAsString(obj)).toString();
            map = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while converting to hashMap:" + e.getMessage(), e);
        }
        return map;
    }

    public static String toJSONString(Object obj) {
        JSONObject jsonObject;
        if (!Optional.ofNullable(obj).isPresent()) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String convertedJSON = "";
        try {
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            jsonObject = toJSONObject(mapper.writeValueAsString(obj));
            convertedJSON = jsonObject.toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error occurred while converting to jsonString" + e.getMessage(), e);
        }
        return convertedJSON;
    }

    public static <T> T toObject(final String jsonString, final Class<T> objClass, boolean failOnUnkownFields) {
        T obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnkownFields);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            obj = mapper.readValue(jsonString, objClass);
        } catch (IOException e) { //@Todo needs to add exception later
            throw new RuntimeException("Error occurred while parsing json string to service specific object" + e.getMessage(), e);
        }
        return obj;
    }


}