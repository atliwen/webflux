package com.example.webflux.router;

import com.alibaba.fastjson.JSON;
import com.example.webflux.router.dome.HttpServeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李文
 * @create 2022-06-29 13:50
 **/
public class HttpServeHandler implements HandlerFunction<ServerResponse>
{

    HttpServeData serveData;

    public HttpServeHandler() {
    }

    public HttpServeHandler(HttpServeData data) {
        this.serveData = data;
    }


    private static final Logger log = LoggerFactory.getLogger(HttpServeHandler.class);

    @Override
    public Mono<ServerResponse> handle(ServerRequest requests) {


        Mono<List<Map<String, Object>>> out = null;

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        // 过滤 httpHeaders 数据
        map.putAll(filter(serveData.getHeaders(), requests.headers().asHttpHeaders().toSingleValueMap()));
        // 过滤 queryParams 数据
        map.putAll(filter(serveData.getQueryParam(), requests.queryParams().toSingleValueMap()));

        MediaType contentType = requests.headers().asHttpHeaders().getContentType();

        return requests.bodyToMono(String.class)
                .doOnNext(c -> map.putAll(filterObject(serveData.getBodyMap(), getBody(c, contentType))))
                .doOnNext(c -> {
                    list.add(map);
                    System.out.println(JSON.toJSONString(list));
                }).flatMap(c -> {
                    System.out.println(JSON.toJSONString(list));
                    return ServerResponse.ok().body(Mono.just(list), List.class);
                }).switchIfEmpty(ServerResponse.ok().body(Mono.just(isEmpty(list,map)), List.class));
    }

    private List<Map<String, Object>> isEmpty(List<Map<String, Object>> list,Map<String, Object> map) {
        list.add(map);
        return list;
    }

    private Map getBody(String originalBody, MediaType contentType) {
        try {
            if (MediaType.APPLICATION_JSON.equals(contentType) || MediaType.APPLICATION_JSON_UTF8.equals(contentType)) {
                return JSON.parseObject(originalBody, Map.class);
            }
            if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(contentType)) {
                return form2Map(originalBody);
            }
        } catch (Exception e) {
            log.error("getBody 异常", e);
        }
        return new HashMap();
    }

    /**
     * 表单字符串转化成 hashMap
     *
     * @param orderinfo
     * @return
     */
    public static Map form2Map(String orderinfo) {
        String listinfo[];
        HashMap<String, String> map = new HashMap<String, String>();
        listinfo = orderinfo.split("&");
        for (String s : listinfo) {
            String list[] = s.split("=");
            if (list.length > 1) {
                map.put(list[0], list[1]);
            }
        }
        return map;
    }


    public static Map<String, Object> filter(Map<String, String> map, Map<String, String> dataMap) {
        Map<String, Object> outMap = new HashMap<>();
        if (dataMap == null || map == null) {
            return outMap;
        }
        map.keySet().forEach(c -> {
            Object o = dataMap.get(c);
            outMap.put(c, o);
        });
        return outMap;
    }

    public static Map<String, Object> filterObject(Map<String, String> map, Map<String, Object> dataMap) {
        Map<String, Object> outMap = new HashMap<>();
        if (dataMap == null || map == null) {
            return outMap;
        }
        map.keySet().forEach(c -> {
            Object o = dataMap.get(c);
            outMap.put(c, o);
        });
        return outMap;
    }
}
