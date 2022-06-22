package com.example.webflux.stream.webclient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-22 16:35
 **/

public class WebClientData
{
    /**
     * 请求类型  GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;
     */
    String httpMethod;
    /**
     * 请求路径  URL 地址
     */
    String urlPath;

    /**
     * URL 传递的参数
     */
    Map<String, String> queryParam;

    /**
     * body 传递的参数
     */
    Map<String, String> bodyMap;

    /**
     * 请求头
     */
    Map<String, String> headers;

    /**
     * 请求头类型  x-www-form-urlencoded, form-data, application/json, text/plain
     */
    String contentType;


    public MediaType getContentType() {
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

    public WebClientData setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public HttpMethod getHttpMethod() {
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

    public WebClientData setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public WebClientData setUrlPath(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }

    public MultiValueMap<String, String> getQueryParam(Map<String, Object> dataMap) {
        if (queryParam == null) return null;
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        getMapValueString(queryParam, dataMap).forEach(multiValueMap::add);
        return multiValueMap;
    }


    public WebClientData setQueryParam(Map<String, String> queryParam) {
        this.queryParam = queryParam;
        return this;
    }

    public Map<String, String> getBodyMap() {
        return bodyMap;
    }

    public WebClientData setBodyMap(Map<String, String> map) {
        this.bodyMap = map;
        return this;
    }

    public HttpHeaders getHeaders(Map<String, Object> map, HttpHeaders c) {
        if (headers != null && headers.size() > 0) {
            getMapValueString(headers, map).forEach(c::add);
        }
        return c;
    }

    public WebClientData setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
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
}
