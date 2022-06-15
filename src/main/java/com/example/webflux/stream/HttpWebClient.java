package com.example.webflux.stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-15 11:16
 **/
public class HttpWebClient
{
    public static void main(String[] args) {
        Data data = new Data();
        data.setUrlPath("http://10.10.12.114:8099/test3/tt");
        data.setContentType("application/json");
        Map<String, String> headers = new HashMap<>();
        headers.put("ua", "ua");
        data.setHeaders(headers);
        data.setHttpMethod("GET");
        Map<String, String> map = new HashMap<>();
        map.put("createBy", "aa");
        data.setBodyMap(map);
        MultiValueMap  map2 = new LinkedMultiValueMap<>();
        map2.add("aa", "AA");
        map2.add("pageNum", "AA");
        map2.add("pageSize", "AA");
        data.setQueryParam(map2);

        System.out.println("输出数据：   " + performed(data).block());
    }

    static WebClient client = WebClient.create();

    //static JSONObject json = new JSONObject();

    public static Mono<Map> performed(Data data) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(data.getUrlPath()).queryParams(data.getQueryParam()).build();
        WebClient.RequestBodySpec spec = client
                .method(data.getHttpMethod())
                .uri(uriComponents.toUriString())
                .headers(c -> setHeaders(data, c))
                .contentType(data.getContentType());

        if (data.getBodyMap() != null) {
            return spec.bodyValue(data.getBodyMap()).retrieve().bodyToMono(Map.class);
        }
        return spec.retrieve().bodyToMono(Map.class);
    }

    private static void setHeaders(Data data, HttpHeaders c) {
        if (data.getHeaders() != null && data.getHeaders().size() > 0) {
            for (Map.Entry<String, String> v : data.getHeaders().entrySet()) {
                c.add(v.getKey(), v.getValue());
            }
        }
    }


    public static class Data
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
        MultiValueMap<String, String> queryParam;

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

        public Data setContentType(String contentType) {
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

        public Data setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public String getUrlPath() {
            return urlPath;
        }

        public Data setUrlPath(String urlPath) {
            this.urlPath = urlPath;
            return this;
        }

        public MultiValueMap<String, String> getQueryParam() {
            return queryParam;
        }

        public Data setQueryParam(MultiValueMap<String, String> queryParam) {
            this.queryParam = queryParam;
            return this;
        }

        public Map<String, String> getBodyMap() {
            return bodyMap;
        }

        public Data setBodyMap(Map<String, String> map) {
            this.bodyMap = map;
            return this;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Data setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }
    }

}
