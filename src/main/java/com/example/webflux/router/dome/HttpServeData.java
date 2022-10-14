package com.example.webflux.router.dome;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.webflux.dome.BaseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-30 14:44
 **/
@Component
public class HttpServeData implements BaseData
{
    /**
     * 请求类型  GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
     */

    //@JSONField(name = "GET", serializeUsing = Serializer.class)
    String httpMethod;
    /**
     * 请求路径  URL 地址
     */
    String urlPath;

    /**
     * URL 传递的参数  key :  字段名称  value ：字段描述
     */
    Map<String, String> queryParam;

    /**
     * body 传递的参数 key :  字段名称  value ：字段描述
     */
    Map<String, String> bodyMap;

    /**
     * 请求头 key :  字段名称  value ：字段描述
     */
    Map<String, String> headers;

    /**
     * 请求头类型  x-www-form-urlencoded, form-data, application/json, text/plain
     */
    String contentType;

    public String getContentType() {
        return this.contentType;
    }
    @JSONField(serialize = false)
    public MediaType getContentTypeMediaType() {
        //请求类型  x-www-form-urlencoded, form-data, application/json, text/plain
        switch (this.contentType) {
            case "application/x-www-form-urlencoded":
                return MediaType.APPLICATION_FORM_URLENCODED;
            case "multipart/form-data":
                return MediaType.MULTIPART_FORM_DATA;
            case "application/json":
                return MediaType.APPLICATION_JSON;
            case "text/plain":
                return MediaType.TEXT_PLAIN;
        }
        return MediaType.TEXT_PLAIN;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;

    }

    public String getHttpMethod() {
        return this.httpMethod;
    }

    @JSONField(serialize = false)
    public HttpMethod getHttpMethodType() {
        //请求类型  GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
        switch (this.httpMethod) {
            case "GET":
                return HttpMethod.GET;
            case "HEAD":
                return HttpMethod.HEAD;
            case "POST":
                return HttpMethod.POST;
            case "PUT":
                return HttpMethod.PUT;
            case "PATCH":
                return HttpMethod.PATCH;
            case "DELETE":
                return HttpMethod.DELETE;
            case "OPTIONS":
                return HttpMethod.OPTIONS;
            case "TRACE":
                return HttpMethod.TRACE;
        }
        return HttpMethod.GET;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;

    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;

    }

    public MultiValueMap<String, String> getQueryParam(Map<String, Object> dataMap) {
        if (queryParam == null) return null;
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        getMapValueString(queryParam, dataMap).forEach(multiValueMap::add);
        return multiValueMap;
    }

    public Map<String, String> getQueryParam() {
        return this.queryParam;
    }

    public void setQueryParam(Map<String, String> queryParam) {
        this.queryParam = queryParam;

    }

    public Map<String, String> getBodyMap() {
        return bodyMap;
    }

    public void setBodyMap(Map<String, String> map) {
        this.bodyMap = map;

    }

    public HttpHeaders getHeaders(Map<String, Object> map, HttpHeaders c) {
        if (headers != null && headers.size() > 0) {
            getMapValueString(headers, map).forEach(c::add);
        }
        return c;
    }

    public Map<String, String> getHeaders() {

        return this.headers;
    }


    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;

    }


    public static Map<String, String> getMapValueString(Map<String, String> param, Map<String, Object> dataMap) {
        for (Map.Entry<String, String> entry : param.entrySet()) {
            //  是否有值
            String value = entry.getValue();
            if (StringUtils.hasText(value)) {
                // 提取数据  如  map = {user,atliwen}  ${user} = atliwen
                if (value.startsWith("${") && value.endsWith("}")) {
                    value = value.substring(2, value.length() - 1);
                    param.put(entry.getKey(), String.valueOf(dataMap.get(value)));
                }
            }
        }
        return param;
    }

    public static Map<String, Object> getMapValueObject(Map<String, String> param, Map<String, Object> dataMap) {
        Map<String, Object> newObjects = new HashMap<>();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            //  是否有值
            String value = entry.getValue();
            if (StringUtils.hasText(value)) {
                // 提取数据  如  map = {user,atliwen}  ${user} = atliwen
                if (value.startsWith("${") && value.endsWith("}")) {
                    value = value.substring(2, value.length() - 1);
                    newObjects.put(entry.getKey(), dataMap.get(value));
                } else {
                    newObjects.put(entry.getKey(), entry.getValue());
                }
            }
        }

        return newObjects;
    }

    @Override
    public String name() {
        return "HttpServeData";
    }

    public static String getFromJson()
    {
        return "[{\"type\":\"input\",\"field\":\"urlPath\",\"title\":\"请求路径\"},{\"type\":\"select\",\"field\":\"httpMethod\",\"title\":\"请求类型\",\"options\":[{\"value\":\"GET\",\"label\":\"GET\"},{\"label\":\"POST\",\"value\":\"POST\"},{\"label\":\"PUT\",\"value\":\"PUT\"},{\"label\":\"DELETE\",\"value\":\"DELETE\"}]},{\"type\":\"select\",\"field\":\"contentType\",\"title\":\"请求头类型\",\"options\":[{\"value\":\"x-www-form-urlencoded\",\"label\":\"x-www-form-urlencoded\"},{\"value\":\"form-data\",\"label\":\"form-data\"},{\"label\":\"application/json\",\"value\":\"application/json\"},{\"label\":\"text/plain\",\"value\":\"text/plain\"}]},{\"type\":\"mapFrom\",\"field\":\"queryParam\",\"title\":\"URL参数\",\"value\":{}},{\"type\":\"mapFrom\",\"field\":\"bodyMap\",\"title\":\"JSON参数\",\"value\":{}},{\"type\":\"mapFrom\",\"field\":\"headers\",\"title\":\"请求头参数\",\"value\":{}}]";
    }
}

